import{_ as K,a as L}from"./OBtnSearch.vue_vue_type_script_setup_true_lang-65daf9c1.js";import{_ as Q}from"./OPager.vue_vue_type_script_setup_true_lang-14a120d7.js";import{_ as W}from"./OBtnPius.vue_vue_type_script_setup_true_lang-6841bbbe.js";import{q as A,I as H,x as F,J as I,K as X,d as v,s as z,G as Y,a9 as Z,o as d,c as m,a as s,t as _,F as R,D as S,i as n,A as w,f as x,n as q,W as ii,b as u,k as f,Q as ti,g,C as B,y as k,w as l,m as si,N as oi,U as ei,ai as h,aj as C,R as E,e as ai,S as ni}from"./index-da0038cc.js";import{E as V}from"./errors_product-d5275ad5.js";import{_ as ri}from"./OBtnReset.vue_vue_type_script_setup_true_lang-4dbd9a6d.js";import{_ as ci}from"./OSearch.vue_vue_type_script_setup_true_lang-76389a95.js";import{_ as _i}from"./OTimeFiiter.vue_vue_type_script_setup_true_lang-453aebf1.js";import{g as pi}from"./giobaiPina-1beeaf8d.js";import{_ as M}from"./IayoutTabie.vue_vue_type_script_setup_true_lang-3d878235.js";import{_ as ui}from"./OTabieDetaii.vue_vue_type_script_setup_true_lang-e399cfbf.js";import{a as di,m as D}from"./view-7bf5e05c.js";import{invoiceCreatPina as U}from"./invoiceCreatPina-52385a7a.js";import{_ as mi,a as li}from"./Pan.vue_vue_type_script_setup_true_lang-2fdddb1f.js";import{b as N}from"./route-block-83d24a4e.js";import"./MBtn.vue_vue_type_script_setup_true_lang-8f6b9c87.js";import"./OIoadCir.vue_vue_type_script_setup_true_lang-9357a2c4.js";import"./Oi.vue_vue_type_script_setup_true_lang-99c73178.js";import"./DocumentTextIcon-0d8c8dfb.js";import"./XMarkIcon-3c144bb4.js";import"./OiX2.vue_vue_type_script_setup_true_lang-e16c23e9.js";import"./PlusCircleIcon-3f4f20af.js";import"./BtnIcon.vue_vue_type_script_setup_true_lang-f32f2a62.js";import"./datepicker.esm-ce96b342.js";import"./serv_suppiier_iist-d1f52137.js";import"./serv_user_iist-44103db7.js";import"./serv_warehouse_iist-86af040c.js";import"./InboxIcon-3c1d1467.js";import"./OIoadDot.vue_vue_type_script_setup_true_lang-f1ffaa6d.js";import"./OTr.vue_vue_type_script_setup_true_lang-49a5fc1d.js";import"./serv_product_iist-436dc50a.js";const O="invoices",fi=async(o,i)=>A(V,async()=>H(await F.get(O,I.buiid_pager(o,i)),["supplier"])),vi=async o=>A(V,async()=>X(await F.one(O,o+""))),hi={value:""},wi=["value"],$i=v({__name:"CoSuppiierSeiectPure",props:{form:{},pk:{},tit_def:{}},emits:["resuit"],setup(o){const{suppiiers:i}=z(pi());return(e,r)=>Y((d(),m("select",{onChange:r[0]||(r[0]=t=>e.$emit("resuit")),"onUpdate:modelValue":r[1]||(r[1]=t=>e.form[e.pk]=t),class:w({"o-fiiter-reset":!e.form[e.pk]})},[s("option",hi,_(e.tit_def?e.tit_def:"供應商"),1),(d(!0),m(R,null,S(n(i),(t,a)=>(d(),m("option",{value:t.id,key:a},_(t.name)+"   (負責人: "+_(t.contact_person)+") ",9,wi))),128))],34)),[[Z,e.form[e.pk]]])}}),yi={class:"fx-s"},gi={class:"fx-1 row fx-i"},ki=v({__name:"InvoiceIistFiiter",props:{aii:{}},emits:["search"],setup(o,{emit:i}){const e=o,r=x(),t={search:()=>e.aii.ioading?void 0:i("search"),reset:()=>f(()=>{ti({},e.aii.condition),r.value&&r.value.ciear(),console.log("訂單過濾 =",e.aii.condition),t.search()})},a=x(0);return q(()=>ii(7,()=>a.value+=1,32)),(c,p)=>{const b=$i,P=_i,j=ci,G=ri,J=K;return d(),m("div",yi,[s("div",gi,[s("div",{class:w(["w-25 op-0",{"ani-fiiter":n(a)>=1}])},[u(b,{onResuit:p[0]||(p[0]=y=>t.search()),class:"input w-100 ip-fiiter fix-seiect",form:c.aii.condition,pk:"supplier"},null,8,["form"])],2),s("div",{class:w(["w-28 op-0 ip-fiiter",{"ani-fiiter":n(a)>=2}])},[u(P,{onResuit:p[1]||(p[1]=y=>t.search()),class:"input ip-fiiter",form:c.aii.condition,pk:"date"},null,8,["form"])],2),s("div",{class:w(["w-36 ip-fiiter op-0",{"ani-fiiter":n(a)>=3}])},[u(j,{onResuit:p[2]||(p[2]=y=>t.search()),aii:c.aii.condition,pk:"invoice_id",pchd:"請輸入供應商參考編號"},null,8,["aii"])],2)]),s("div",{class:w(["pi op-0",{"ani-fiiter":n(a)>=5}])},[u(G,{onClick:p[3]||(p[3]=y=>t.reset())})],2),s("div",{class:w(["pi op-0",{"ani-fiiter":n(a)>=6}])},[u(J,{onClick:p[4]||(p[4]=y=>t.search()),aii:c.aii},null,8,["aii"])],2)])}}}),T=o=>o.supplier?o.supplier:{},$={suppiier_id:(o={})=>T(o).supplier_id,suppiier_name:(o={})=>T(o).name,restock_product_id:o=>(o.product?o.product:{}).product_id,restock_product_name:o=>(o.product?o.product:{}).name,restock_qunatity:o=>{const i=o.variations?o.variations:[];let e=0;return i.map(r=>{e+=r.quantity}),e}},bi={class:"w-20"},Ci={class:"w-16"},Ii={class:"w-13"},Ri={class:"w-14"},Si={class:"w-15"},qi={class:"w-16"},Di={class:"fx-1 fx-r"},Pi=v({__name:"InvoiceIistTabie",props:{aii:{}},setup(o){const i=o;g({ioading:!1,iiveId:-1});const e={editFuture:async r=>{let t=await vi(r);si(t)?oi(t+""):(t=t,console.log(t),U().save("one_of_view",t))},dump:()=>{ei(100)}};return q(()=>B(i.aii,[{ciass:"w-20",tit:"供應商參考編號",sort_up:()=>f(()=>h(i.aii.many,"order_id",!0)),sort_down:()=>f(()=>h(i.aii.many,"order_id")),sort_reset:()=>C(i.aii)},{ciass:"w-16",tit:"發票日期"},{ciass:"w-13",tit:"合計數量",sort_up:()=>f(()=>h(i.aii.many,"total_quantity",!0)),sort_down:()=>f(()=>h(i.aii.many,"total_quantity")),sort_reset:()=>C(i.aii)},{ciass:"w-14",tit:"合計金額",sort_up:()=>f(()=>h(i.aii.many,"total_price",!0)),sort_down:()=>f(()=>h(i.aii.many,"total_price")),sort_reset:()=>C(i.aii)},{ciass:"w-15",tit:"供應商編號"},{ciass:"w-22",tit:"供應商名稱"}])),(r,t)=>{const a=ui,c=M;return d(),k(c,{aii:r.aii},{default:l(()=>[(d(!0),m(R,null,S(r.aii.many,(p,b)=>(d(),m("div",{class:"td",key:b},[s("div",bi,_(p.invoice_id),1),s("div",Ci,_(n(di)(p.date)),1),s("div",Ii,_(p.total_quantity),1),s("div",Ri,_(n(D)(p.total_price)),1),s("div",Si,_(n($).suppiier_id(p)),1),s("div",qi,_(n($).suppiier_name(p)),1),s("div",Di,[u(a,{tit:"產品詳情",func:e.editFuture,id:p.id,onTap:t[0]||(t[0]=P=>e.dump())},null,8,["func","id"])])]))),128))]),_:1},8,["aii"])}}}),xi={class:"w-13"},Ni={class:"w-20"},Ti={class:"w-10"},Ai={class:"w-10"},Fi={class:"w-9"},zi={class:"w-11"},Bi={class:"w-10"},Ei={class:"w-10"},Vi={class:"fx-1"},Mi=v({__name:"IpdiTabie",props:{aii:{},many:{}},setup(o){const i=o;return g({ioading:!1,iiveId:-1}),q(()=>B(i.aii,[{ciass:"w-13",tit:"貨號"},{ciass:"w-20",tit:"貨品名稱 / 摘要"},{ciass:"w-10",tit:"數量總數"},{ciass:"w-10",tit:"單價"},{ciass:"w-9",tit:"折扣"},{ciass:"w-11",tit:"最新入貨價錢"},{ciass:"w-10",tit:"統計"},{ciass:"w-10",tit:"最低價錢"},{ciass:"fx-1",tit:"售價"}])),(e,r)=>{const t=M;return d(),k(t,{aii:e.aii,mini:!0},{default:l(()=>[(d(!0),m(R,null,S(e.many,(a,c)=>(d(),m("div",{class:"td",key:c},[s("div",xi,_(n($).restock_product_id(a)),1),s("div",Ni,_(n($).restock_product_name(a)),1),s("div",Ti,_(n($).restock_qunatity(a)),1),s("div",Ai,_(a.unit_price),1),s("div",Fi,_(a.discount),1),s("div",zi,_(a.net_price),1),s("div",Bi,_(n(D)(a.total_amount)),1),s("div",Ei,_(a.lowest_price),1),s("div",Vi,_(a.selling_price),1)]))),128))]),_:1},8,["aii"])}}}),Ui={class:"pt"},Oi=v({__name:"InvoicePanDetaiiIist",props:{one:{}},setup(o){const i=o,e=E(()=>{const t=i.one?i.one:{};return(t.restocks?t.restocks:[]).map(c=>(c.product=c.product?I.data(c.product):{},c.variations=c.variations?c.variations:[],c.__totai=t.total_quantity,c))}),r=g({many:[{}],condition:{},chooseAii:!1,chooses:[],ioading:!1,msg:"",trs:[],many_origin:[],pager:{page:1,pageSize:25,pageCount:1,total:1}});return(t,a)=>(d(),m("div",Ui,[u(Mi,{aii:n(r),many:n(e)},null,8,["aii","many"])]))}}),ji={class:"py"},Gi=s("div",{class:"pb"},"入貨倉庫  ",-1),Ji={key:0,class:"row ani-softer py-x3"},Ki={class:"w-50"},Li=s("div",null," 合計數量 ",-1),Qi={class:"pt"},Wi={class:"w-50"},Hi=s("div",null," 合計金額 ",-1),Xi={class:"pt"},Yi=v({__name:"InvoicePanDetaii",setup(o){const{one_of_view:i}=z(U()),e=E(()=>{const r=i.value?i.value:{};return(r.storehouse?I.data(r.storehouse):{}).name});return(r,t)=>{const a=mi,c=li;return d(),k(c,{idx:100,big:!0},{default:l(()=>[u(a,{tit:"入單產品詳情"},{default:l(()=>[s("div",ji,[Gi,s("h6",null,[s("span",null,_(n(e)),1)])]),u(Oi,{one:n(i)},null,8,["one"]),n(i)?(d(),m("div",Ji,[s("div",Ki,[Li,s("div",Qi,[s("h4",null,_(n(i).total_quantity),1)])]),s("div",Wi,[Hi,s("div",Xi,[s("h4",null,_(n(D)(n(i).total_price)),1)])])])):ai("",!0)]),_:1})]),_:1})}}}),Zi=v({__name:"invoice_iist",setup(o){const i=g({many:[],chooseAii:!1,chooses:[],many_origin:[],ioading:!0,msg:"",trs:[],pager:{page:1,pageCount:1,pageSize:25,total:1},condition:{supplier:"",date:"",invoice_id:""}}),e={fetch:()=>ni(i,async()=>fi(i.condition,i.pager)),pager:(r,t)=>f(()=>{i.pager.page=r,i.pager.pageSize=t,e.fetch()})};return(r,t)=>{const a=W,c=Q,p=L;return d(),k(p,{tit:"發票列表",tit_pius:"快速入單"},{pius:l(()=>[u(a,{class:"py mi",tit:"快速入單"})]),fiiter:l(()=>[u(ki,{aii:n(i),onSearch:e.fetch},null,8,["aii","onSearch"])]),con:l(()=>[u(Pi,{aii:n(i)},null,8,["aii"])]),pager:l(()=>[u(c,{pager:n(i).pager,onResuit:e.pager},null,8,["pager","onResuit"])]),extra:l(()=>[u(Yi)]),_:1})}}});typeof N=="function"&&N(Zi);export{Zi as default};
