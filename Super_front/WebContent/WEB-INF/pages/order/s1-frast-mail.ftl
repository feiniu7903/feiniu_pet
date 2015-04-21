    <!-- 默认可编辑状态 -->
        <div id="frast-edit" class="form-edit <#if usrReceiversList?? && usrReceiversList?size gt 0>hide</#if>">
            <#list usrReceiversList as obj> 
            <input type="hidden" id="frastID" value="${obj.receiverId}" >
            <dl class="xdl">
                <dt><i class="req">*</i>收件人姓名：</dt>
                <dd>
                    <input type="text" name="text-frast" class="input-text" value="${obj.receiverName}" />
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>手机号码：</dt>
                <dd>
                    <input type="text" name="text-frast" class="input-text" value=" ${obj.mobileNumber}" />
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>省份：</dt>
                <dd>
                    <select data-class="selectbox-small" name="province" id="">
                       		<#list provinceList as province>
									<option value="${province.provinceId}" <#if usrReceivers.province == province.provinceId>selected</#if>> ${province.provinceName}</option>
							</#list>
				    </select>
                    <select data-class="selectbox-small" name="city" id="">
                        <#if cityList??&&cityList?size >0>
									<#list cityList as city>
										<option value="${city.cityId}" <#if city.cityId == usrReceivers.city>selected</#if>>${city.cityName}</option>
									</#list>
						<#else>
									  	<option value="">${obj.city}</option>										
						</#if>
                    </select>
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>收件地址：</dt>
                <dd>
                    <input type="text" class="input-text input-big" name="text-frast" value=" ${obj.address}" />
                </dd>
            </dl>
            <dl class="xdl">
                <dt>邮编：</dt>
                <dd>
                    <input type="text" class="input-text input-mini" name="text-frast" value="${obj.postCode}" />
                </dd>
            </dl>
            <dl class="xdl">
                <dt></dt>
                <dd>
                    <button id="frast-submit" class="pbtn pbtn-mini pbtn-blue">保存</button>
                </dd>
            </dl>
             </#list>
        </div>
       
        <!-- 保存后状态 -->
       
        <div id="frast-info" class="form-info <#if !usrReceiversList?? && usrReceiversList?size lt 0>hide</#if>">
           <#list usrReceiversList as obj> 
           	<#if obj_index==0>
           	<input type="hidden" name="receiverId" id="frastID" value="${obj.receiverId}" >
           	<input type="hidden" name="receiverName" value="${obj.receiverName}" >
           	<input type="hidden" name="mobileNumber" value="${obj.mobileNumber}" >
           	<input type="hidden" name="province" value="${obj.province} " >
           	<input type="hidden" name="city" value="${obj.city}" >
           	<input type="hidden" name="address" value="${obj.address}" >
           	<input type="hidden" name="postcode" value="${obj.postCode}" >
           	
            <dl class="xdl">
                <dt><i class="req">*</i>收件人姓名：</dt>
                <dd>
                 ${obj.receiverName}
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>手机号码：</dt>
                <dd>
                  ${obj.mobileNumber}
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>省份：</dt>
                <dd>
                  ${obj.province}  ${obj.city}
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>收件地址：</dt>
                <dd>
                ${obj.address}
                </dd>
            </dl>
            <dl class="xdl">
                <dt>邮编：</dt>
                <dd>
                 ${obj.postCode}
                </dd>
            </dl>
            <dl class="xdl">
                <dt></dt>
                <dd>
                    <button id="frast-btn" class="pbtn pbtn-mini pbtn-light">修改</button>
                </dd>
            </dl>
           </#if>
            </#list>
        </div>
         
        <!-- //保存后状态 -->
