<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>无标题文档</title>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<link href="http://pic.lvmama.com/styles/new_v/help_center/hc_index.css" rel="stylesheet" type="text/css" />
</head>
<body>

    <div class="hc_fq_cont">
      <h4 class="hc_hot_fq">热门问题</h4>
      <ul class="common_fq">
      <@s.iterator value="map.get('${station}_hot_question')" status="st">
        <li><a href="${url?if_exists}" target="_top">${title?if_exists}</a></li>
      </@s.iterator>
      </ul>
      <h4 class="hc_hot_fq side_margin">快速引导</h4>
      <div class="common_fq">
      <@s.iterator value="map.get('${station}_help_guide')" status="st">
        <div class="fast_list">
          <h5 class="kefu_bg kefu_bg033">${title?if_exists}<a href="${url?if_exists}" class="more_add" target="_top">更多 »</a></h5>
          <ul class="fast_list_01">
          <@s.iterator value="map.get('${station}_help_guide_${st.index}')" status="st">
            <li><a href="${url?if_exists}" target="_top">${title?if_exists}</a></li>
          </@s.iterator>  
          </ul>
        </div>
      </@s.iterator>  
      </div> 
    </div>

</body>
</html>
