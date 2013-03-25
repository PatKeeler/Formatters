select pi.service_id, pi.service_version, pi.Active, ci.component_name, ci.scid,
ip.vpn_id, ip.Long_Description from pipeline_SI_ProductInstance pi
inner join pipeline_SI_ComponentInstance ci on pi.transaction_guid = ci.transaction_guid
AND ci.Component_Name = 'IP Drain Loc' AND ci.Include_In_Quote IN (1,2)
left join pipeline_SI_CD_IPDrainLoc ip on ci.component_instance_guid = ip.component_instance_guid 
where ci.SCID = 'BBFJ12570' and ci.LCID = "BBFJ12570" order by pi.Service_ID, pi.Service_Version, ip.SCID