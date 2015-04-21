 <@s.if test="prodProduct.isTicket()">
					<span class="proType"></span><strong>门票
				</@s.if>
				<@s.elseif test="prodProduct.isGroup()">
				    <span class="proType proType3"></span><strong>国内游
				</@s.elseif>
				<@s.elseif test="prodProduct.isFreeness()">
					<span class="proType proType2"></span><strong>自由行
				</@s.elseif>
				<@s.elseif test="prodProduct.isForeign()">
					<span class="proType proType4"></span><strong>出境游
				</@s.elseif>
				<@s.elseif test="prodProduct.isHotel()">
					<span class="proType proType5"></span><strong>国内酒店
</@s.elseif>
