package com.lvmama.comm.hessian;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.util.StringUtils;

/**
 * 远程Service扫描器
 * 
 * <pre>
 * <b>自动扫描并加载远程Service</b>
 * </pre>
 * 
 * @author yanggan
 * 
 */
public class RemoteServiceScanner implements BeanFactoryPostProcessor, InitializingBean,
        ApplicationContextAware {


	private static Log log = LogFactory.getLog(RemoteServiceScanner.class);
	
    private ApplicationContext applicationContext;
    
    private String basePackage;

    private String remoteServer;
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void afterPropertiesSet() throws Exception {

    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setRemoteServer(this.remoteServer);
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));

    }

    private final class Scanner extends ClassPathBeanDefinitionScanner {

    	private String remoteServer;
    	
    	static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
        private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
        private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
    	private String resourcePattern = DEFAULT_RESOURCE_PATTERN;
    	
        private BeanDefinitionRegistry registry;

        public Scanner(BeanDefinitionRegistry registry) {
            super(registry);
            this.registry = registry;
        }

        public void setRemoteServer(String remoteServer) {
			this.remoteServer = remoteServer;
		}
        
        @Override
        protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
            Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
            for (String basePackage : basePackages) {
                Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
                for (BeanDefinition candidate : candidates) {
                    ScopeMetadata scopeMetadata = this.scopeMetadataResolver
                            .resolveScopeMetadata(candidate);
                    candidate.setScope(scopeMetadata.getScopeName());
                    ScannedGenericBeanDefinition bd = (ScannedGenericBeanDefinition) candidate;
                    bd.setBeanClassName(HessianProxyFactoryBean.class.getName());
                    bd.setBeanClass(HessianProxyFactoryBean.class);
                    AnnotatedBeanDefinition annotatedDef = (AnnotatedBeanDefinition)candidate;
                    AnnotationMetadata amd = annotatedDef.getMetadata();
                    Set<String> types = amd.getAnnotationTypes();
                    String beanName = "";
                    //远程Service名称
                    String remoteService = "";
                    //远程服务地址
                    String remoteServer = "";
                    boolean chunkedPost = false;
                    boolean overloadEnabled = false;
                    for(String type:types){
                        if(type.equals(RemoteService.class.getName())){
                            Map<String, Object> attributes = amd.getAnnotationAttributes(type);
                            String value = (String) attributes.get("value");
                            if (StringUtils.hasLength(value)) {
                                beanName = value;
                            }
                            value = (String) attributes.get("remoteService");
                            if (StringUtils.hasLength(value)) {
                            	remoteService = value;
                            }
                            value = (String) attributes.get("remoteServer");
                            if (StringUtils.hasLength(value)) {
                            	remoteServer = value;
                            }
                            Boolean value_b = (Boolean) attributes.get("chunkedPost");
                            if (value_b!=null) {
                            	chunkedPost = value_b;
                            }
                            value_b = (Boolean) attributes.get("overloadEnabled");
                            if (value_b!=null) {
                            	overloadEnabled = value_b;
                            }
                        }
                    }
                    if(StringUtils.hasLength(remoteServer)){
                    	Properties properties = new Properties(); 
                    	try {
							properties.load(this.getClass().getClassLoader().getResourceAsStream("const.properties"));
							remoteServer =properties.getProperty(remoteServer);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    }else{
                    	remoteServer = this.remoteServer;
                    }
                    if(!StringUtils.hasLength(remoteService)){
                    	remoteService = beanName;
                    }
                    String serviceUrl = remoteServer+ "/" + remoteService;
                    String interf = amd.getClassName();
                    bd.getPropertyValues().add("serviceInterface", interf);
                    bd.getPropertyValues().add("serviceUrl", serviceUrl);
                    bd.getPropertyValues().add("chunkedPost", chunkedPost);
                    bd.getPropertyValues().add("overloadEnabled", overloadEnabled);
                    BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate,beanName );
                    definitionHolder = applyScopedProxyMode(scopeMetadata, definitionHolder,
                            this.registry);
                    beanDefinitions.add(definitionHolder);
                    registerBeanDefinition(definitionHolder, this.registry);
                }
            }
            if (beanDefinitions.isEmpty()) {
                log.debug("not service be scaned");
            } else {
                for (BeanDefinitionHolder holder : beanDefinitions) {
                    AnnotatedBeanDefinition definition = (AnnotatedBeanDefinition) holder
                            .getBeanDefinition();
                    log.debug(holder.getBeanName());
                    log.debug( applicationContext.getBean(holder.getBeanName()));
                    log.debug(definition.getMetadata().getAnnotationTypes());
                }
            }

            return beanDefinitions;

        }

        @Override
        protected void registerDefaultFilters() {

            addIncludeFilter(new AnnotationTypeFilter(RemoteService.class));
        }
        @Override
        public Set<BeanDefinition> findCandidateComponents(String basePackage) {
    		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
    		try {
    			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
    					resolveBasePackage(basePackage) + "/" + this.resourcePattern;
    			Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
    			boolean traceEnabled = logger.isTraceEnabled();
    			boolean debugEnabled = logger.isDebugEnabled();
    			for (Resource resource : resources) {
    				if (traceEnabled) {
    					logger.trace("Scanning " + resource);
    				}
    				if (resource.isReadable()) {
    					try {
    						MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
    						if (isCandidateComponent(metadataReader)) {
    							ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
    							sbd.setResource(resource);
    							sbd.setSource(resource);
    							if (debugEnabled) {
									logger.debug("Identified candidate component class: " + resource);
								}
    							candidates.add(sbd);
    						}
    						else {
    							if (traceEnabled) {
    								logger.trace("Ignored because not matching any filter: " + resource);
    							}
    						}
    					}
    					catch (Throwable ex) {
    						throw new BeanDefinitionStoreException(
    								"Failed to read candidate component class: " + resource, ex);
    					}
    				}
    				else {
    					if (traceEnabled) {
    						logger.trace("Ignored because not readable: " + resource);
    					}
    				}
    			}
    		}
    		catch (IOException ex) {
    			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
    		}
    		return candidates;
    	}

        BeanDefinitionHolder applyScopedProxyMode(
                ScopeMetadata metadata, BeanDefinitionHolder definition,
                BeanDefinitionRegistry registry) {

            ScopedProxyMode scopedProxyMode = metadata.getScopedProxyMode();
            if (scopedProxyMode.equals(ScopedProxyMode.NO)) {
                return definition;
            }
            boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
            return ScopedProxyUtils.createScopedProxy(definition, registry, proxyTargetClass);
        }

    }

	public String getRemoteServer() {
		return remoteServer;
	}

	public void setRemoteServer(String remoteServer) {
		this.remoteServer = remoteServer;
	}

}
