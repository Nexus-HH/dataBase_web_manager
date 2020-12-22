$(function(){
  let resString = localStorage.getItem('resString');
  if(resString==null) localStorage.setItem('resString','');
  let statusString = localStorage.getItem('statusString');
  if(statusString==null) localStorage.setItem('statusString','');
  let timeString = localStorage.getItem('timeString');
  if(timeString==null) localStorage.setItem('timeString','');
  const res = localStorage.getItem('res')
  const status = localStorage.getItem('status');
  let time = localStorage.getItem('time');

  if(res!=''){
    resString = res + "+" + resString;
    localStorage.setItem('res','');
    localStorage.setItem('resString',resString);
  }
  if(status!=''){
    statusString = status + "+" + statusString;
    localStorage.setItem('status','');
    localStorage.setItem('statusString',statusString);
  }
  if(time!=''){
    timeString = time + "*" + timeString;
    localStorage.setItem('time','');
    localStorage.setItem('timeString',timeString);
  }

  let resArray= (localStorage.getItem('resString')).split('+');
  let statusArray = (localStorage.getItem('statusString')).split('+');
  let timeArray = (localStorage.getItem('timeString')).split('*');

  function loopArray(){
    statusArray.forEach((val,i,arr)=>{
      if(val=='200'){
        const json_res = JSON.parse(resArray[i]);
        const t = timeArray[i].split(' ');
        let span = `<span class="time">${t[3]}-${t[1]}-${t[2]}-${t[4]}</span>`;
        let li = `<li class="new">${json_res.operator}${json_res.table} ${json_res.username}成功 ${span}</li>`;
        $('.container').prepend(li);
      }
      else if(val!=''){
        const json_res = JSON.parse(resArray[i]);
        const t = timeArray[i].split(' ');
        let span = `<span class="time">${t[3]}-${t[1]}-${t[2]}-${t[4]}</span>`;
        let li = `<li class="new">${json_res.operator}${json_res.table} ${json_res.username}失败，http状态码${val} ${span}</li>`;
        $('.container').prepend(li);
      }
    })
  }
  loopArray();

  $('.clear').on('click',()=>{
    localStorage.setItem('resString','');
    localStorage.setItem('statusString','');
    localStorage.setItem('timeString','');
    $('.container').children(".new").remove();
  });

  $('#showSql').on('click',()=>{
    window.location.href = 'showSql.html';
  })

})

