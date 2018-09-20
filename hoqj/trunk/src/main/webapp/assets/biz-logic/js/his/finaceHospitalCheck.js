/**导出*/
$(function() {
  $('#excelExport').click(function(){
    var bDate = $('#bDate').datebox('getValue'); 
    var eDate = $("#eDate").datebox('getValue');
    document.location.href = encodeURI("finaceHospitalCheck/mz_export.html?bDate=" + bDate+"&eDate="+eDate);
  });
});