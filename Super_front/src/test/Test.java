
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.util.classloader.ReloadingClassLoader;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterface;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterfaceDelegate;
import com.opensymphony.xwork2.util.finder.UrlSet;
import com.opensymphony.xwork2.util.TextParseUtil;

public class Test {
	// private static final Logger LOG = LoggerFactory.getLogger(PackageBasedActionConfigBuilder.class);
	    private  Configuration configuration;
	  //  private final ActionNameBuilder actionNameBuilder;
	   // private final ResultMapBuilder resultMapBuilder;
	   // private final InterceptorMapBuilder interceptorMapBuilder;
	    private  ObjectFactory objectFactory;
	    private  String defaultParentPackage;
	    private  boolean redirectToSlash;
	    private String[] actionPackages;
	    private String[] excludePackages;
	    private String[] packageLocators;
	    private String[] includeJars;
	    private String packageLocatorsBasePackage;
	    private boolean disableActionScanning = false;
	    private boolean disablePackageLocatorsScanning = false;
	    private String actionSuffix = "Action";
	    private boolean checkImplementsAction = true;
	    private boolean mapAllMatches = false;
	    private Set<String> loadedFileUrls = new HashSet<String>();
	    private boolean devMode;
	    private ReloadingClassLoader reloadingClassLoader;
	    private boolean reload;
	    private Set<String> fileProtocols;
	    private boolean alwaysMapExecute;
	    private boolean excludeParentClassLoader;

	    private static final String DEFAULT_METHOD = "execute";
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		 String[] a = ".*?/super_*.jar".split("\\s*[,]\\s*");
		  Test t = new Test();
		  t.setFileProtocols("jar,vfsfile,vfszip");
		  t.setIncludeJars(".*?/super_*.jar(!/)?");
		  UrlSet urlSet = t.buildUrlSet();
		  for (int i = 0; i < urlSet.getUrls().size(); i++) {
			System.out.println(i+" urlSet "+urlSet.getUrls().get(i));
		}
	}
	   public void setIncludeJars(String includeJars) {
	        if (StringUtils.isNotEmpty(includeJars))
	            this.includeJars = includeJars.split("\\s*[,]\\s*");
	    }
    protected boolean isReloadEnabled() {
        return devMode && reload;
    }
    
    protected ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    protected ClassLoaderInterface getClassLoaderInterface() {
        if (isReloadEnabled())
            return new ClassLoaderInterfaceDelegate(this.reloadingClassLoader);
        else {
            /*
            if there is a ClassLoaderInterface in the context, use it, otherwise
            default to the default ClassLoaderInterface (a wrapper around the current
            thread classloader)
            using this, other plugins (like OSGi) can plugin their own classloader for a while
            and it will be used by Convention (it cannot be a bean, as Convention is likely to be
            called multiple times, and it needs to use the default ClassLoaderInterface during normal startup)
            */
            ClassLoaderInterface classLoaderInterface = null;
            ActionContext ctx = ActionContext.getContext();
            if (ctx != null)
                classLoaderInterface = (ClassLoaderInterface) ctx.get(ClassLoaderInterface.CLASS_LOADER_INTERFACE);

            return (ClassLoaderInterface) ObjectUtils.defaultIfNull(classLoaderInterface, new ClassLoaderInterfaceDelegate(getClassLoader()));
        }
    }
    public void setFileProtocols(String fileProtocols) {
        if (StringUtils.isNotBlank(fileProtocols)) {
            this.fileProtocols = TextParseUtil.commaDelimitedStringToSet(fileProtocols);
        }
    }
	 public UrlSet buildUrlSet() throws IOException {
	        ClassLoaderInterface classLoaderInterface = getClassLoaderInterface();
	        UrlSet urlSet = new UrlSet(classLoaderInterface, this.fileProtocols);
	        for (int i = 0; i < urlSet.getUrls().size(); i++) {
				System.out.println(i+" urlSet "+urlSet.getUrls().get(i));
			}
	        
	        //excluding the urls found by the parent class loader is desired, but fails in JBoss (all urls are removed)
	        if (excludeParentClassLoader) {
	            //exclude parent of classloaders
	            ClassLoaderInterface parent = classLoaderInterface.getParent();
	            //if reload is enabled, we need to step up one level, otherwise the UrlSet will be empty
	            //this happens because the parent of the realoding class loader is the web app classloader
	            if (parent != null && isReloadEnabled())
	                parent = parent.getParent();

	            if (parent != null)
	                urlSet = urlSet.exclude(parent);

	            try {
	                // This may fail in some sandboxes, ie GAE
	                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
	                urlSet = urlSet.exclude(new ClassLoaderInterfaceDelegate(systemClassLoader.getParent()));

	            } catch (SecurityException e) {
	            	e.printStackTrace();
	                            }
	        }

	        //try to find classes dirs inside war files
	        urlSet = urlSet.includeClassesUrl(classLoaderInterface);


	        urlSet = urlSet.excludeJavaExtDirs();
	        urlSet = urlSet.excludeJavaEndorsedDirs();
	        try {
	        	urlSet = urlSet.excludeJavaHome();
	        } catch (NullPointerException e) {
	        	// This happens in GAE since the sandbox contains no java.home directory
	           e.printStackTrace();
	        }
	        urlSet = urlSet.excludePaths(System.getProperty("sun.boot.class.path", ""));
	        urlSet = urlSet.exclude(".*/JavaVM.framework/.*");

	        if (includeJars == null) {
	            urlSet = urlSet.exclude(".*?\\.jar(!/|/)?");
	        } else {
	            //jar urls regexes were specified
	            List<URL> rawIncludedUrls = urlSet.getUrls();
	            Set<URL> includeUrls = new HashSet<URL>();
	            boolean[] patternUsed = new boolean[includeJars.length];
	            System.out.println();
	            for (URL url : rawIncludedUrls) {
	            	System.out.println(fileProtocols+" "+url.getProtocol()+"  is： "+fileProtocols.contains(url.getProtocol()));
	                //if (fileProtocols.contains(url.getProtocol())) {
	                    //it is a jar file, make sure it macthes at least a url regex
	                    for (int i = 0; i < includeJars.length; i++) {
	                        String includeJar = includeJars[i];
	                        System.out.println(includeJar+" "+url.toExternalForm()+"  is： "+Pattern.matches(includeJar, url.toExternalForm()));
	                        if (Pattern.matches(includeJar, url.toExternalForm())) {
	                            includeUrls.add(url);
	                            patternUsed[i] = true;
	                            break;
	                        }
	                    }
	                //} else {
	                    //it is not a jar
	                    includeUrls.add(url);
	                //}
	            }
	        
	            return new UrlSet(includeUrls);
	        }

	        return urlSet;
	    }
}
