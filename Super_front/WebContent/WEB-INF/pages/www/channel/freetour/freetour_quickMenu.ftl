
<div class="quick-menu">
	<h3>当季热门周边游</h3>
	<div class="quick-list">
		<div class="quickicon"></div>
		<div class="menu-item">
			<div class="menu-itembox">
				<span class="icon-rarr"></span>
				<h4>
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_MAIN_1')"
					status="st"> <@s.if test="#st.first"><a target="_blank"
						href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</h4>
				<p class="item">
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage
					}_MENU_MAIN_1')" status="st"> <@s.if test="!(#st.first)"><a
						target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</p>
			</div>
			<div class="quick-menu-drop">
				<div class="drop-item">
					<h5>热门目的地</h5>
					<div class="item-hor">
                           <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_UP_1')" status="st">
 	                          <@s.if test="#st.last"><a rel="nofollow" target="_blank" class="link-more" href="${url?if_exists}">${title?if_exists}&raquo;</a></@s.if>
 	                          <@s.else>
 	                          <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
 	                          </@s.else>
                            </@s.iterator>
                            
                    </div>
					<div class="list">
						<h5>热销产品</h5>
						<div class="item-ver">
							<@s.iterator
							value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_DOWN_1')"
							status="st"> <@s.if test="!(#st.last)"> <a target="_blank"
								href="${url?if_exists}"><i class="c${st.count }"></i>${title?if_exists}</a>
							</@s.if> <@s.else> <a rel="nofollow" class="link-more fr" target="_blank"
								href="${url?if_exists}">${title?if_exists}&raquo;</a> </@s.else>
							</@s.iterator>

						</div>
					</div>
				</div>
				<div class="drop-list">
					<p>
						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_RIGHT_1')"
						status="st"> <a rel="nofollow" target="_blank" href="${url?if_exists}"><img
							data-imgsrc="${imgUrl?if_exists}" width="300" height="200"
							alt="${title?if_exists}" /></a> <a class="limit" target="_blank"
							href="${url?if_exists}"><@s.if test="remark!=null &&
							remark.length()>65"> <@s.property value="remark.substring(0,65)"
							escape="false"/>... </@s.if><@s.else>${remark?if_exists}
							</@s.else></a> </@s.iterator>
					</p>
				</div>
			</div>
		</div>
		<div class="menu-item">
			<div class="menu-itembox">
				<span class="icon-rarr"></span>
				<h4>
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage
					}_MENU_MAIN_2')" status="st"> <@s.if test="#st.first"><a
						target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</h4>
				<p class="item">
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage
					}_MENU_MAIN_2')" status="st"> <@s.if test="!(#st.first)"><a
						target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</p>
			</div>
			<div class="quick-menu-drop">
				<div class="drop-item">
					<h5>热门目的地</h5>
					<div class="item-hor">

						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_UP_2')"
						status="st"> <@s.if test="!(#st.last)"> <a target="_blank"
							href="${url?if_exists}">${title?if_exists}</a> </@s.if> <@s.else>
						<a rel="nofollow"  target="_blank" class="link-more" href="${url?if_exists}">${title?if_exists}&raquo;</a>
						</@s.else> </@s.iterator>
					</div>
					<div class="list">
						<h5>热销产品</h5>
						<div class="item-ver">
							<@s.iterator
							value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_DOWN_2')"
							status="st"> <@s.if test="!(#st.last)"> <a target="_blank"
								href="${url?if_exists}"><i class="c${st.count }"></i>${title?if_exists}</a>
							</@s.if> <@s.else> <a rel="nofollow"  class="link-more fr" target="_blank"
								href="${url?if_exists}">${title?if_exists}&raquo;</a> </@s.else>
							</@s.iterator>
						</div>

					</div>
				</div>
				<div class="drop-list">
					<p>
						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_RIGHT_2')"
						status="st"> <a rel="nofollow"  target="_blank" href="${url?if_exists}"><img
							data-imgsrc="${imgUrl?if_exists}" width="300" height="200"
							alt="${title?if_exists}" /></a> <a class="limit" target="_blank"
							href="${url?if_exists}"><@s.if test="remark!=null &&
							remark.length()>65"> <@s.property value="remark.substring(0,65)"
							escape="false"/>... </@s.if><@s.else>${remark?if_exists}
							</@s.else></a> </@s.iterator>
					</p>
				</div>
			</div>
		</div>
		<div class="menu-item">
			<div class="menu-itembox">
				<span class="icon-rarr"></span>
				<h4>
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage
					}_MENU_MAIN_3')" status="st"> <@s.if test="#st.first"><a
						target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</h4>
				<p class="item">
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage
					}_MENU_MAIN_3')" status="st"> <@s.if test="!(#st.first)"><a
						target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>

				</p>
			</div>
			<div class="quick-menu-drop">
				<div class="drop-item">
					<h5>热门目的地</h5>
					<div class="item-hor">
						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_UP_3')"
						status="st"> <@s.if test="!(#st.last)"> <a target="_blank"
							href="${url?if_exists}">${title?if_exists}</a> </@s.if> <@s.else>
						<a rel="nofollow"  target="_blank" class="link-more" href="${url?if_exists}">${title?if_exists}&raquo;</a>
						</@s.else> </@s.iterator>
					</div>
					<div class="list">
						<h5>热销产品</h5>
						<div class="item-ver">
							<@s.iterator
							value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_DOWN_3')"
							status="st"> <@s.if test="!(#st.last)"> <a target="_blank"
								href="${url?if_exists}"><i class="c${st.count }"></i>${title?if_exists}</a>
							</@s.if> <@s.else> <a rel="nofollow"  class="link-more fr" target="_blank"
								href="${url?if_exists}">${title?if_exists}&raquo;</a> </@s.else>
							</@s.iterator>
						</div>
					</div>
				</div>
				<div class="drop-list">
					<p>
						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_RIGHT_3')"
						status="st"> <a rel="nofollow"  target="_blank" href="${url?if_exists}"><img
							data-imgsrc="${imgUrl?if_exists}" width="300" height="200"
							alt="${title?if_exists}" /></a> <a target="_blank" class="limit"
							href="${url?if_exists}"><@s.if test="remark!=null &&
							remark.length()>65"> <@s.property value="remark.substring(0,65)"
							escape="false"/>... </@s.if><@s.else>${remark?if_exists}
							</@s.else></a> </@s.iterator>
					</p>
				</div>
			</div>
		</div>
		<div class="menu-item">
			<div class="menu-itembox">
				<span class="icon-rarr"></span>
				<h4>
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage
					}_MENU_MAIN_4')" status="st"> <@s.if test="#st.first"><a
						target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</h4>
				<p class="item">
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage
					}_MENU_MAIN_4')" status="st"> <@s.if test="!(#st.first)"><a
						target="_blank" href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</p>
			</div>
			<div class="quick-menu-drop">
				<div class="drop-item">
					<h5>热门目的地</h5>
					<div class="item-hor">
						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_UP_4')"
						status="st"> <@s.if test="!(#st.last)"> <a target="_blank"
							href="${url?if_exists}">${title?if_exists}</a> </@s.if> <@s.else>
						<a rel="nofollow"  target="_blank" class="link-more" href="${url?if_exists}">${title?if_exists}&raquo;</a>
						</@s.else> </@s.iterator>
					</div>
					<div class="list">
						<h5>热销产品</h5>
						<div class="item-ver">
							<@s.iterator
							value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_DOWN_4')"
							status="st"> <@s.if test="!(#st.last)"> <a target="_blank"
								href="${url?if_exists}"><i class="c${st.count }"></i>${title?if_exists}</a>
							</@s.if> <@s.else> <a rel="nofollow"  class="link-more fr" target="_blank"
								href="${url?if_exists}">${title?if_exists}&raquo;</a> </@s.else>
							</@s.iterator>
						</div>
					</div>
				</div>
				<div class="drop-list">
					<p>
						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_RIGHT_4')"
						status="st"> <a rel="nofollow"  target="_blank" href="${url?if_exists}"><img
							data-imgsrc="${imgUrl?if_exists}" width="300" height="200"
							alt="${title?if_exists}" /></a> <a  target="_blank" class="limit"
							href="${url?if_exists}"><@s.if test="remark!=null &&
							remark.length()>65"> <@s.property value="remark.substring(0,65)"
							escape="false"/>... </@s.if><@s.else>${remark?if_exists}
							</@s.else></a> </@s.iterator>
					</p>
				</div>
			</div>
		</div>
		<div class="menu-item menu-last">
			<div class="menu-itembox">
				<span class="icon-rarr"></span>
				<h4>
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_MAIN_5')"
					status="st"> <@s.if test="#st.first"><a target="_blank"
						href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</h4>
				<p class="item">
					<@s.iterator
					value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_MAIN_5')"
					status="st"> <@s.if test="!(#st.first)"><a target="_blank"
						href="${url?if_exists}">${title?if_exists}</a></@s.if>
					</@s.iterator>
				</p>
			</div>
			<div class="quick-menu-drop">
			<div class="drop-item">
					<h5>热门目的地</h5>
					<div class="item-hor">
						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_UP_5')"
						status="st"> <@s.if test="!(#st.last)"> <a target="_blank"
							href="${url?if_exists}">${title?if_exists}</a> </@s.if> <@s.else>
						<a rel="nofollow"  target="_blank" class="link-more" href="${url?if_exists}">${title?if_exists}&raquo;</a>
						</@s.else> </@s.iterator>
					</div>
					<div class="list">
 							    <h5>热门推荐</h5> 
								<div class="drop-recommend"> 
								<ul>
								    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_DOWN_5')" status="st">
										<@s.if test="#st.last"></@s.if>
										<@s.else>
											 <li><b>${bakWord1?if_exists}</b><a target="_blank" href="${url?if_exists}">${title?if_exists}</a></li> 
										</@s.else> 
									</@s.iterator>
								</ul> 
								</div> 
							   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_LEFT_DOWN_5')" status="st">
										<@s.if test="#st.last"><a rel="nofollow" class="link-more fr" target="_blank" href="${url?if_exists}">${title?if_exists} &raquo;</a></@s.if>
										<@s.else>
 										</@s.else> 
							   </@s.iterator>
                        </div>
			</div>
			    
                        
				<div class="drop-list">
					<p>
						<@s.iterator
						value="(map.get('recommendInfoMainList')).get('${channelPage}_MENU_RIGHT_5')"
						status="st"> <a rel="nofollow"  target="_blank" href="${url?if_exists}"><img
							data-imgsrc="${imgUrl?if_exists}" width="300" height="200"
							alt="${title?if_exists}" /></a> <a target="_blank" class="limit"
							href="${url?if_exists}"><@s.if test="remark!=null &&
							remark.length()>65"> <@s.property value="remark.substring(0,65)"
							escape="false"/>... </@s.if><@s.else>${remark?if_exists}
							</@s.else></a> </@s.iterator>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- //.quick-menu -->
