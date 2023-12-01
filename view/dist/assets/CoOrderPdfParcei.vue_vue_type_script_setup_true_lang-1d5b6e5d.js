import{m as v,c as E}from"./view-7bf5e05c.js";import{d as m,o,c as e,a as s,t as c,i as _,R as k,B as h,F as $,D as y,r as T,b as a,w as f,A as x,y as H,e as L,f as O,g as S,n as N,z as V,k as K}from"./index-da0038cc.js";import{v as g}from"./vai_cashier_order-f199484b.js";import{v as w}from"./vai_order-1f2fc4b5.js";import{_ as D}from"./CuHtmIogo.vue_vue_type_script_setup_true_lang-6c530f64.js";const F={class:""},M=s("div",{class:"pdf-hr"},null,-1),R={class:"pt-pdf-x2 fx-r"},q=s("h4",{class:"pr-pdf n"},"統計金額:",-1),z={class:"fw-pdf-700"},A=m({__name:"CoOpTotai",props:{one:{}},setup(u){return(t,i)=>(o(),e("div",F,[M,s("div",R,[q,s("h2",z,"HK "+c(_(v)(t.one.total_price)),1)])]))}}),I={class:"px-pdf"},W=s("h2",{class:"pb-pdf"},"付款",-1),J={class:"ih-pdf-son-s"},j={class:"fx-s"},G=s("p",null,"產品",-1),Q={class:"w-50 fx-r"},U={class:"sus"},X={class:"mw-7em ta-r b"},Y={class:"fx-s fx-t"},Z=s("p",null,"支付方式",-1),ss={class:"w-50"},os={class:""},es={class:"sus"},ts={class:"mw-7em ta-r b"},ns={class:"pt"},_s={class:"fx-s fx-t"},cs=s("p",null,"優惠類別",-1),is={key:0,class:"w-50"},ds={class:"sus"},rs={class:"mw-7em ta-r b"},as={key:1,class:"w-50 fx-r sus"},ps=m({__name:"CoOpPrice",props:{one:{}},setup(u){const t=u,i=k(()=>g.product_aii_num(t.one)),p=k(()=>g.product_aii_price(t.one));return(d,r)=>(o(),e("div",I,[W,s("div",J,[s("div",j,[G,h(),s("div",Q,[s("p",U,"數量"+c(_(i)),1),s("p",X,c(_(v)(_(p))),1)])]),s("div",Y,[Z,s("div",ss,[s("div",os,[(o(!0),e($,null,y(d.one.payment_method,(n,l)=>(o(),e("div",{class:"fx-r",key:l},[s("p",es,c(n.name),1),s("p",ts,c(_(v)(n.price)),1)]))),128))])])])]),s("div",ns,[s("div",_s,[cs,d.one.discount&&d.one.discount.length>0?(o(),e("div",is,[(o(!0),e($,null,y(d.one.discount,(n,l)=>(o(),e("div",{class:"fx-r",key:l},[s("p",ds,c(n.type),1),s("p",rs," -"+c(_(v)(n.discount_deduction)),1)]))),128))])):(o(),e("div",as," (無) "))])])]))}}),ls={class:"pdf-bd br px-pdf py-pdf"},C=m({__name:"CoBdWrapper",setup(u){return(t,i)=>(o(),e("div",ls,[T(t.$slots,"default")]))}}),us=s("p",{class:"b fs-s-pdf"},"帳單展示備註 :",-1),fs={key:0,class:"fs-s-pdf"},hs=["innerHTML"],ms=s("br",null,null,-1),vs={key:1,class:"fs-s-pdf"},$s=s("br",null,null,-1),ys=s("br",null,null,-1),ks=s("br",null,null,-1),ws=m({__name:"CoOpRemark",props:{one:{}},setup(u){const t=u,i=k(()=>g.array_remarks_for_order(t.one));return(p,d)=>{const r=C;return o(),e("div",null,[a(r,{class:"_wrapper"},{default:f(()=>[us,_(i)&&_(i).length>0?(o(),e("div",fs,[(o(!0),e($,null,y(_(i),(n,l)=>(o(),e("div",{key:l},[s("p",{innerHTML:n},null,8,hs),ms]))),128))])):(o(),e("div",vs,[h(" 暫無備註 "),$s,ys,ks]))]),_:1})])}}}),bs={class:"pi-pdf-s"},xs=s("h4",null,"產品",-1),gs={class:"fx-s ih-pdf-son-x1"},Cs={class:"w-30"},Os={class:"b pb-pdf-t"},Ts={class:"sus fs-s-pdf"},Bs={class:"w-17 fs-s-pdf sus"},Ps={class:"d-ib"},Es={class:"w-18 fs-s-pdf sus"},Hs={class:"w-35 fx-s"},Ls={class:"fs-s-pdf d-ib sus"},Ss={class:"fx-1 b d-ib ta-r ih-pdf-son-1"},Ns=m({__name:"CoOpProds",props:{one:{}},setup(u){return(t,i)=>{const p=C;return o(),e("div",null,[a(p,{class:"_wrapper"},{default:f(()=>[s("div",bs,[xs,(o(!0),e($,null,y(t.one.ordered_product,(d,r)=>(o(),e("div",{class:"pt-pdf",key:r},[s("div",gs,[s("div",Cs,[s("p",Os,c(_(w).order_product(d).name),1),s("p",Ts,"樣式:  "+c(_(w).order_product_variation(d).name),1)]),s("div",Bs,[h("附加:  "),s("div",Ps,"- "+c(d.discount_deduction),1)]),s("div",Es,"單價: "+c(d.selling_price),1),s("div",Hs,[s("div",Ls," 數量: "+c(d.quantity),1),s("div",Ss,c(_(v)(d.total_price))+" HKD ",1)])])]))),128))])]),_:1})])}}}),Vs={class:"fx-s fx-t ih-pdf-son-x1"},Ks=m({__name:"CoOpTitV",props:{tit:{},tit_ciass:{},v_ciass:{}},setup(u){return(t,i)=>(o(),e("div",Vs,[s("p",{class:x([t.tit_ciass,"mw-5em"])},c(t.tit),3),s("p",{class:x(["b ta-r fx-1 eiip-2",t.v_ciass])},[T(t.$slots,"default")],2)]))}}),Ds={class:"fx-s"},Fs={class:"fx-1"},Ms=s("div",{class:"w-1em"},null,-1),Rs={class:"fx-1"},qs={key:0},zs={key:1},As={key:0},Is={key:1},Ws=m({__name:"CoOpMsg",props:{one:{}},setup(u){const t=u,i=k(()=>t.one.order_shop?t.one.order_shop:{});return(p,d)=>{const r=Ks,n=C;return o(),e("div",Ds,[s("div",Fs,[a(n,{class:"px-pdf"},{default:f(()=>[a(r,{tit:"日期",v_ciass:"mw-6em ta-i"},{default:f(()=>[h(c(_(E)(p.one.order_date,!0)),1)]),_:1}),a(r,{tit:"訂單編號",v_ciass:"mw-6em ta-i"},{default:f(()=>[h(c(p.one.order_id),1)]),_:1}),a(r,{tit:"會員名稱",v_ciass:"mw-6em ta-i"},{default:f(()=>[h(c(_(w).member(p.one,"普通會員")),1)]),_:1}),a(r,{tit:"收銀員",v_ciass:"mw-6em ta-i"},{default:f(()=>[h(c(_(w).cashier(p.one)),1)]),_:1})]),_:1})]),Ms,s("div",Rs,[a(n,{class:"px-pdf"},{default:f(()=>[a(r,{tit:"店鋪電話"},{default:f(()=>[_(i).id?(o(),e("span",qs,c(_(i).phone_no),1)):(o(),e("span",zs,"Hello Kitty 官方"))]),_:1}),a(r,{tit:"關注/FOLLOW"},{default:f(()=>[_(i).facebook?(o(),e("span",As,c(_(i).facebook),1)):(o(),e("span",Is,"Hello Kitty 官方"))]),_:1}),a(r,{tit:"店鋪地址",v_ciass:"mh-2em-pdf"},{default:f(()=>[h(c(_(i).address),1)]),_:1}),_(i).id?L("",!0):(o(),H(r,{key:0},{default:f(()=>[h(" ")]),_:1}))]),_:1})])])}}}),Js={class:"w-100 bg-con pdf-wrapper co-order-pdf"},js={class:"fx-s"},Gs=s("h2",null,"銷售數據 SALE RECEIPT",-1),Qs=m({__name:"CoOrderPdfPapper",props:{one:{}},setup(u){return(t,i)=>{const p=D,d=Ws,r=Ns,n=ws,l=ps,b=A;return o(),e("div",Js,[s("div",js,[Gs,s("div",null,[a(p,{class:"pdf-iogo"})])]),a(d,{one:t.one,class:"pt-pdf-x1"},null,8,["one"]),a(r,{one:t.one,class:"pt-pdf-x2 co-op-prods"},null,8,["one"]),a(n,{one:t.one,class:"pt-pdf-x2 co-op-remark"},null,8,["one"]),a(l,{one:t.one,class:"pt-pdf-x2"},null,8,["one"]),a(b,{one:t.one,class:"pt-1em"},null,8,["one"])])}}}),oo=m({__name:"CoOrderPdfParcei",props:{ciass:{},kiii_printed:{type:Boolean}},setup(u){const t=u,i=O(!0),p=O({}),d=S({many:[]}),r={remark_than_num:n=>{n.map(()=>{})},spiite_order:()=>{},init:()=>K(()=>{const n=sessionStorage.getItem("heiiokitty_order_of_printed");if(n){const l=JSON.parse(n);l&&l.id&&(p.value=l)}t.kiii_printed||setTimeout(()=>{i.value&&window.print()},800)}),effect:()=>{const n=p.value;n&&n.id&&(d.many.length=0,(n.ordered_product?n.ordered_product:[]).length>5,d.many.push(n))}};return N(r.init),V(r.effect),(n,l)=>{const b=Qs;return o(),e("div",null,[(o(!0),e($,null,y(_(d).many,(B,P)=>(o(),e("div",{class:"fx-c",key:P},[a(b,{class:x(n.ciass),one:B},null,8,["class","one"])]))),128))])}}});export{oo as _};
