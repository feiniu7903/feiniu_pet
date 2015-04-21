        <div class="quick-menu menu-custom">
	        <h3>定制游分类目录</h3>
	        <div class="quick-list">
	    		<div class="menu-item">
                    <div class="menu-itembox">
                        <span class="icon-rarr"></span>
                        <h4><a href="#">上海周边</a></h4>
                        <p class="item">
                        <@s.iterator value="navigatorMap.get('${station}_shzbtj')" status="st"> <!--推荐城市-->
                            <a target="_blank" href="${url?if_exists}" style="a">${title?if_exists}</a>
                        </@s.iterator>
                        </p>
                    </div>
	    			<div class="quick-menu-drop">
	    			    <div class="droplist-item">
                            <dl>
						  <@s.iterator value="navigatorMap.get('${station}_shzb')" status="st"> <!--省份-->
	    					    <dt>${title?if_exists}</dt>
	    						<dd class="item-hor">
								<@s.iterator value="navigatorMap.get('${station}_sf_${st.index + 1}')" status="sts"><!--城市-->
	    						    <a target="_blank" href="http://www.lvmama.com/company/city/${bakWord1?if_exists}">${title?if_exists}</a>
								</@s.iterator>                                     
	    						</dd>
						</@s.iterator> 	  
	    					</dl>

	    				</div>
	    			</div>
	    		</div>
	    		<div class="menu-item">
                    <div class="menu-itembox">
                        <span class="icon-rarr"></span>
                        <h4><a href="#">长途游</a></h4>
                        <p class="item">
                             <@s.iterator value="navigatorMap.get('${station}_cttj')" status="st"> <!--推荐城市-->
                            <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                        </@s.iterator> 	
                            
                        </p>
                    </div>
	    			<div class="quick-menu-drop">
	    			    <div class="droplist-item">
	    				    <div class="item">
	    						<dl>
	    						<@s.iterator value="navigatorMap.get('${station}_ct1')" status="st"> <!--省份1-->
	    					    <dt>${title?if_exists}</dt>
	    						<dd class="item-hor">
								<@s.iterator value="navigatorMap.get('${station}_ct1_${st.index + 1}')" status="sts"><!--城市-->
	    						    <a target="_blank" href="http://www.lvmama.com/company/city/${bakWord1?if_exists}">${title?if_exists}</a>
								</@s.iterator>                                     
	    						</dd>
                                </@s.iterator> 	
	    						</dl>
	    						
	    					</div>
	    					<div class="item">
	    						<dl>
                                <@s.iterator value="navigatorMap.get('${station}_ct2')" status="st"> <!--省份2-->
	    					    <dt>${title?if_exists}</dt>
	    						<dd class="item-hor">
								<@s.iterator value="navigatorMap.get('${station}_ct2_${st.index + 1}')" status="sts"><!--城市-->
	    						    <a  target="_blank" href="http://www.lvmama.com/company/city/${bakWord1?if_exists}">${title?if_exists}</a>
								</@s.iterator>                                     
	    						</dd>
                                </@s.iterator>
	    						</dl>
	    						
	    					</div>
	    					<div class="item">
	    						<dl>
	    						<@s.iterator value="navigatorMap.get('${station}_ct3')" status="st"> <!--省份3-->
	    					    <dt>${title?if_exists}</dt>
	    						<dd class="item-hor">
								<@s.iterator value="navigatorMap.get('${station}_ct3_${st.index + 1}')" status="sts"><!--城市-->
	    						    <a target="_blank" href="http://www.lvmama.com/company/city/${bakWord1?if_exists}">${title?if_exists}</a>
								</@s.iterator>                                     
	    						</dd>
                                </@s.iterator>
	    						</dl>
	    						
	    					</div>
	    				</div>
	    			</div>
	    		</div>
	    		<div class="menu-item">
                    <div class="menu-itembox">
                        <span class="icon-rarr"></span>
                        <h4><a href="#">出境游</a></h4>
                        <p class="item">
                         <@s.iterator value="navigatorMap.get('${station}_cjtj')" status="st"> <!--推荐国家-->
                            <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                        </@s.iterator> 
                    </div>
	    			</p>
	    			<div class="quick-menu-drop">
	    			    <div class="droplist-item">
	    				    <dl>
	    					    <@s.iterator value="navigatorMap.get('${station}_cj')" status="st"> <!--大洲-->
	    					    <dt>${title?if_exists}</dt>
	    						<dd class="item-hor">
								<@s.iterator value="navigatorMap.get('${station}_cj_${st.index + 1}')" status="sts"><!--城市-->
	    						    <a target="_blank" href="http://www.lvmama.com/company/city/${bakWord1?if_exists}">${title?if_exists}</a>
								</@s.iterator>                                     
	    						</dd>
                                </@s.iterator>
	    					</dl>
	
	    				</div>
	    			</div>
	    		</div>
	    		<div class="menu-item menu-last">
                    <div class="menu-itembox">
                        <span class="icon-rarr"></span>
                        <h4><a href="#">摄影、拓展游</a></h4>
                        <p class="item">
                             <@s.iterator value="navigatorMap.get('${station}_sytztj')" status="st"> <!--推荐摄影、拓展-->
                            <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                        </@s.iterator>
                        </p>
                    </div>
	    			<div class="quick-menu-drop">
	    			    <div class="droplist-item">
	    				    <dl>
	    					     <@s.iterator value="navigatorMap.get('${station}_sytz')" status="st"> <!--摄影拓展-->
	    					    <dt>${title?if_exists}</dt>
	    						<dd class="item-hor">
								<@s.iterator value="navigatorMap.get('${station}_sytz_${st.index + 1}')" status="sts"><!--类型-->
	    						    <a target="_blank" href="http://www.lvmama.com/company/city/${bakWord1?if_exists}">${title?if_exists}</a>
								</@s.iterator>                                     
	    						</dd>
                                </@s.iterator>
	    					</dl>
	    					
	    				</div>
	    			</div>
	    		</div>
	        </div>
	    </div> <!-- //.quick-menu -->