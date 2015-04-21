<div class="s2-info-area">
    <h3>发票信息</h3>
      <dl>
      	<dt class="dt-150px"><span>*</span>请先选择您是否需要发票：</dt>
        <dd class="line-label"><input type="radio" id="needInvoiceRadio" value="true" name="buyInfo.needInvoice" align="absmiddle" checked="checked" /><label>是</label>
        <input type="radio" value="false" name="buyInfo.needInvoice" align="absmiddle" /><label>否</label></dd>
		<dt><span>*</span>发票抬头：</dt>
	    <dd><input type="text" name="ordInvoice.title" id="invoiceTitle" /></dd>
		<dt><span>*</span>发票明细：</dt>
	    <dd class="celect-margin">
	    	<select name="ordInvoice.detail" id="invoiceDetail">
			<@s.iterator value="invoiceDetails" id="detail">
				<option value="${detail.code }">
					${detail.name }
				</option>
			</@s.iterator>
			</select>
		</dd>
		<dt>发票备注：</dt>
	    <dd><textarea name="ordInvoice.memo" cols="80" rows="4" id="invoiceMemo" onfocus="$(this).val('');" onchange="this.unbind('focus');">您对订单的特殊要求</textarea></dd>
	</dl>
</div>
