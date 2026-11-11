const BASE=import.meta.env.VITE_API_URL||(window.location.port==='5173'?'http://localhost:8080/api':'/api');
export const token=()=>localStorage.getItem('trackflow_token');
export async function api(path,options={}){const res=await fetch(`${BASE}${path}`,{...options,headers:{'Content-Type':'application/json',...(token()?{Authorization:`Bearer ${token()}`}:{ }),...options.headers}});if(res.status===204)return null;const data=await res.json().catch(()=>({}));if(!res.ok)throw new Error(data.message||'Something went wrong');return data}
export const socketUrl=()=>{const url=new URL(BASE,window.location.href);url.protocol=url.protocol==='https:'?'wss:':'ws:';url.pathname='/ws';return url.toString()};
