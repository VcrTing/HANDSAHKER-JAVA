import{_ as U}from"./CoProduChoiseMod.vue_vue_type_script_setup_true_lang-713f2034.js";import{_ as b}from"./CoWarehouseSeiect.vue_vue_type_script_setup_true_lang-6e768b3b.js";import{_ as F}from"./OTime.vue_vue_type_script_setup_true_lang-c92770f4.js";import{_ as G}from"./OInput.vue_vue_type_script_setup_true_lang-d4d8d4ec.js";import{_ as R}from"./OInputI.vue_vue_type_script_setup_true_lang-f1b74179.js";import{d as S,g as T,a8 as j,s as E,h as n,R as y,o as _,c as p,a as d,b as s,w as m,as as H,i as a,$,G as f,H as w,y as J,a9 as L,F as O,D as P,t as z,e as A,a4 as I,J as K,ag as l}from"./index-da0038cc.js";import{c as Q}from"./choiseOnePina-6238de78.js";const W={class:"o-form"},X=["value"],Y=d("option",{value:0},"-- 請選擇 --",-1),Z=["value"],se=S({__name:"BadGoodsCreatBase",props:{form:{},aii:{}},setup(g){const o=g,q=["product_id","variation","quantity","storehouse_id","date","remarks"],i=T(j(o.form)),{product_id:c,storehouse_id:v,product_of_choise:h}=E(Q());n(c,e=>o.form.product_id=e),n(v,e=>{o.form.storehouse_id=e});const B=y(()=>{const e=h.value;return e.id?`[${e.product_id}]  ${e.name}`:""}),C=y(()=>{let e=h.value.variations;return I(e)?e:K.iist(e)});return n(()=>o.aii.sign,()=>{o.form.storehouse_id=v.value,q.map(e=>{if(l(i,e,o.form[e],o.aii)){o.aii.can=!1;return}}),o.aii.can=!0}),n(()=>o.form.variation,e=>l(i,"variation",e,o.aii)),n(()=>o.form.quantity,e=>l(i,"quantity",e,o.aii)),n(()=>o.form.date,e=>l(i,"date",e,o.aii)),(e,t)=>{const k=R,u=G,V=F,D=b,M=U;return _(),p("div",null,[d("div",W,[s(k,{tit:"壞貨產品",err:a(i).product_id,icon:"product",onClick:t[1]||(t[1]=r=>a($)(1e3))},{default:m(()=>[d("input",{onClick:t[0]||(t[0]=H(r=>a($)(1e3),["stop"])),value:a(B),placeholder:"請點擊後打開選擇框"},null,8,X)]),_:1},8,["err"]),s(u,{tit:"數量",err:a(i).quantity},{default:m(()=>[f(d("input",{type:"number","onUpdate:modelValue":t[2]||(t[2]=r=>e.form.quantity=r),placeholder:"請輸入"},null,512),[[w,e.form.quantity]])]),_:1},8,["err"]),a(c)?(_(),J(u,{key:0,tit:"樣式",err:a(i).variation},{default:m(()=>[f(d("select",{"onUpdate:modelValue":t[3]||(t[3]=r=>e.form.variation=r)},[Y,(_(!0),p(O,null,P(a(C),(r,N)=>(_(),p("option",{key:N,value:r.id},z(r.name),9,Z))),128))],512),[[L,e.form.variation]])]),_:1},8,["err"])):A("",!0),s(k,{tit:"日期",err:a(i).date,icon:"date"},{default:m(()=>[s(V,{form:e.form,pk:"date"},null,8,["form"])]),_:1},8,["err"]),s(u,{tit:"壞貨位置",err:a(i).storehouse_id},{default:m(()=>[s(D)]),_:1},8,["err"]),s(u,{tit:"備註",err:!1},{default:m(()=>[f(d("textarea",{rows:"3","onUpdate:modelValue":t[4]||(t[4]=r=>e.form.remarks=r),placeholder:"請輸入"},null,512),[[w,e.form.remarks]])]),_:1})]),s(M)])}}});export{se as _};
