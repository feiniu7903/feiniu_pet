<@s.if test="orderCount>prodProduct.groupMin">
		<div class="hongqi"><span class="imgQi" style="left:155px;" ></span></div>
		<div class="jdt">
								<span class="imgTiaoLeft"></span>
								<span class="imgTiaoMid" style="width:150px;"></span>
								<span class="imgTiaoRight"></span>
		</div>
		<div class="qiNum">
								<span class="numRight">${prodProduct.groupMin}</span>
								<span class="numLeft">0</span>
		</div>
</@s.if>
<@s.if test="prodProduct.groupMin !=null && prodProduct.groupMin>0 && orderCount<=prodProduct.groupMin">
<div class="jingDu">
		<div class="hongqi"><span class="imgQi" style="left:${orderCount/prodProduct.groupMin*155}px;"></span></div>
		<div class="jdt">
								<span class="imgTiaoLeft"  ></span>
								<span class="imgTiaoMid" style="width:${orderCount/prodProduct.groupMin*150}px;"></span>
								<span class="imgTiaoRight"></span>
		</div>
		<div class="qiNum">
								<span class="numRight">${prodProduct.groupMin}</span>
								<span class="numLeft">0</span>
		</div>
</div>
</@s.if>
