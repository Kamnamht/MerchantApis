<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pub="http://xmlns.oracle.com/oxp/service/PublicReportService">
   <soapenv:Header/>
   <soapenv:Body>
      <pub:runReport>
         <pub:reportRequest>
            <pub:reportAbsolutePath>/APBL_REPORTS/API/GST_Merchant_Invoice_param.xdo</pub:reportAbsolutePath>
                      <pub:parameterNameValues>
               <!--Zero or more repetitions:-->
               <pub:item>
                  <pub:dataType>String</pub:dataType>
                  <pub:name>p_actor_id</pub:name>
                  <pub:values>
                     <!--Zero or more repetitions:-->
                     <pub:item>${actor.actor_id}</pub:item>
                  </pub:values>
               </pub:item>
                <pub:item>
                  <pub:dataType>String</pub:dataType>
                  <pub:name>p_month</pub:name>
                  <pub:values>
                     <!--Zero or more repetitions:-->
                     <pub:item>${actor.month}</pub:item>
                  </pub:values>
               </pub:item>
            </pub:parameterNameValues>

          
          
         </pub:reportRequest>
         <pub:userID>api</pub:userID>
         <pub:password>airtel@123</pub:password>
      </pub:runReport>
   </soapenv:Body>
</soapenv:Envelope>
