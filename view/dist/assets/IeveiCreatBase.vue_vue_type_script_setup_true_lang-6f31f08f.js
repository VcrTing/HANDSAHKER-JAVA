import{_ as v}from"./OInput.vue_vue_type_script_setup_true_lang-d4d8d4ec.js";import{d as B,g,a8 as h,h as n,o as w,c as V,b as m,w as d,G as f,H as u,a as l,i as p,ag as s}from"./index-da0038cc.js";import{b}from"./verify-18da9a0d.js";const C={class:"o-form"},U=B({__name:"IeveiCreatBase",props:{form:{},aii:{},edit:{type:Boolean}},setup(c){const a=c,_=["name","discount"],r=g(h(a.form));return n(()=>a.aii.sign,()=>{let e=!0;a.aii.can=!1,_.map(o=>{s(r,o,a.form[o],a.aii)&&(e=!1)}),a.aii.can=e}),n(()=>a.form.name,e=>s(r,"name",e,a.aii)),n(()=>a.form.discount,e=>s(r,"discount",e,a.aii,()=>!b(e))),(e,o)=>{const i=v;return w(),V("div",C,[m(i,{tit:"等級名稱",err:p(r).name},{default:d(()=>[f(l("input",{"onUpdate:modelValue":o[0]||(o[0]=t=>e.form.name=t),placeholder:"請輸入"},null,512),[[u,e.form.name]])]),_:1},8,["err"]),m(i,{tit:"購買折扣",err:p(r).discount},{default:d(()=>[f(l("input",{type:"number","onUpdate:modelValue":o[1]||(o[1]=t=>e.form.discount=t),placeholder:"請輸入 0 - 1 之間的數字"},null,512),[[u,e.form.discount]])]),_:1},8,["err"])])}}});export{U as _};
