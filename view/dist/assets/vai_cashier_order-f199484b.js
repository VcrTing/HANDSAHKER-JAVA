import{J as m}from"./index-da0038cc.js";import{f as d}from"./fioat-e27af979.js";const s=t=>t.ordered_product?t.ordered_product:[],e=t=>{const r=t.product?t.product:{};return r&&m.data(r)},c=(t,r="")=>((t.remarks?t.remarks:[]).map(o=>{r+=o.content?o.content+" ":""}),r),k={order_products:s,group_remark_of_product:c,product_aii_num:(t,r=0)=>(s(t).map(o=>{r+=o.quantity?o.quantity:0}),r),product_aii_price:(t,r=0)=>(s(t).map(o=>{r=d.floatAdd(r,o.total_price?o.total_price:0)}),r),remarks_of_order:(t,r="")=>(s(t).map(o=>{r+='<div class="_div">';const a=e(o);r+=c(a),r+="</div>"}),r),array_remarks_for_order:t=>{const r=[];return s(t).map(o=>{const a=e(o);(a.remarks?a.remarks:[]).map(p=>{r.push(p.content)})}),Array.from(new Set(r))},member_name:t=>{const r=t.member?t.member:{};return r.name?r.name:"(未選擇客戶)"}};export{k as v};
