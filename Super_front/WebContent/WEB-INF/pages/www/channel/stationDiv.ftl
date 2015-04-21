<div class="pa_line"></div>
<dl>
                            <dt>华北</dt>
                            <dd>
                               <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_HUABEI')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                               </@s.iterator>
                            </dd>
                        </dl>
                        <dl>
                            <dt>华东</dt>
                            <dd>
                               <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_HUADONG')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                               </@s.iterator>
                            </dd>
                        </dl>
                        <dl>
                            <dl>
                                <dt>东北</dt>
                                <dd>
                                   <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_DONGBEI')" status="st">
                                      <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                                   </@s.iterator>
                                </dd>
                            </dl>
                            <dt>中南</dt>
                            <dd>
                                 <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_ZHONGNAN')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                               </@s.iterator>
                            </dd>
                        </dl>
                        <dl>
                            <dt>西南</dt>
                            <dd>
                               <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_XINAN')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                               </@s.iterator>
                            </dd>
                        </dl>
                        <dl>
                            <dt>西北</dt>
                            <dd>
                               <@s.iterator value="recommendInfoMap.get('${channelPage}_STATION_XIBEI')" status="st">
                                  <a  cityId="${bakWord1?if_exists}" provinceId="${bakWord2?if_exists}"   href="#">${title?if_exists}</a>
                               </@s.iterator>
                            </dd>
                        </dl> 