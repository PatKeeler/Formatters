select 1 as a , '' as b_d , '' as b_f , '' as b_i , "Klassierung - KLS"."KLS - RGM_TYPE" as 
"RGM_TYPE" , "Klassierung - KLS"."KLS - RGM_CODE" as "RGM_CODE" , "Klassierung - KLS"."KLS - BEZ_D" as 
"RGM_BEZ_D" , "Klassierung - KLS"."KLS - BEZ_I" as "RGM_BEZ_I" , "Klassierung - KLS"."KLS - BEZ_F" as 
"RGM_BEZ_F" , "Versicherungszweig"."CODE_VZWG - VZW_I_KURZ_BEZ" as "VZW_KURZ_BEZ_I" , 
"Versicherungszweig"."CODE_VZWG - VZW_F_KURZ_BEZ" as "VZW_KURZ_BEZ_F" , "Versicherungszweig"."CODE_VZWG - 
VZW_D_KURZ_BEZ" as "VZW_KURZ_BEZ_D" , "Praemienart"."ART_CODE - PRM_ART_CODE" as "PRM_ART_CODE" , '18-83' 
as "UNF_JAHR" , "Statistikjahr"."STAT_JAHR - JAHR_4_STELLIG" as "STAT_JAHR" , "Versicherungszweig"."CODE_VZWG 
- VZW_CODE" as "CODE_VZWG" , sum("Kennzahlen"."ABBAU_DER_AUSGLEICHSRESERVE") as "ABBAU_DER_AUSGLEICHSRESERVE", 
sum("Kennzahlen"."AMORTISATIONSBETRAEGE") as "AMORTISATIONSBETRAEGE" , sum("Kennzahlen"."ANZ_BT_QUELLEN") 
as "ANZ_BT_QUELLEN" , sum("Kennzahlen"."ANZ_FAELLE_TOT") as "ANZ_FAELLE_TOT" , sum("Kennzahlen"."ANZ_UNF_PRO_MIO_LS") 
as "ANZ_UNF_PRO_MIO_LS" , sum("Kennzahlen"."ANZ_UNF_PRO_TSD_VB") as "ANZ_UNF_PRO_TSD_VB" , sum("Kennzahlen"."AUFWAND") 
as "AUFWAND" , sum("Kennzahlen"."AUFWAND_HK_TG") as "AUFWAND_HK_TG" , sum("Kennzahlen"."AUFWAND_HK_TG_ZU_TOTAL") 
as "AUFWAND_HK_TG_ZU_TOTAL" , sum("Kennzahlen"."AUFWAND_OHNE_ZWR") as "AUFWAND_OHNE_ZWR" , 
sum("Kennzahlen"."AUFWAND_PRO_UNF") as "AUFWAND_PRO_UNF" , sum("Kennzahlen"."EINMALZAHLUNG") as "EINMALZAHLUNG" , 
sum("Kennzahlen"."FAELLE_BAGATELL") as "FAELLE_BAGATELL" , sum("Kennzahlen"."FAELLE_IE") as "FAELLE_IE" , 
sum("Kennzahlen"."FAELLE_INV") as "FAELLE_INV" , sum("Kennzahlen"."FAELLE_IR") as "FAELLE_IR" , 
sum("Kennzahlen"."FAELLE_ORDENTLICH") as "FAELLE_ORDENTLICH" , sum("Kennzahlen"."FAELLE_TG") as "FAELLE_TG" , 
sum("Kennzahlen"."FAELLE_VERMUT_RENTE") as "FAELLE_VERMUT_RENTE" , sum("Kennzahlen"."GUTSCHRIFTEN") as "GUTSCHRIFTEN" , 
sum("Kennzahlen"."HK") as "HK" , sum("Kennzahlen"."HK_BK") as "HK_BK" , sum("Kennzahlen"."HK_UNF") as "HK_UNF" , 
sum("Kennzahlen"."IE") as "IE" , sum("Kennzahlen"."IE_BK") as "IE_BK" , sum("Kennzahlen"."IE_UNF") as "IE_UNF" , 
sum("Kennzahlen"."INV") as "INV" , sum("Kennzahlen"."INV_BK") as "INV_BK" , sum("Kennzahlen"."INV_UNF") as "INV_UNF" , 
sum("Kennzahlen"."KAPITALERTRAGSUEBERSCHUSS") as "KAPITALERTRAGSUEBERSCHUSS" , sum("Kennzahlen"."KOLLEK_BELAST") as 
"KOLLEK_BELAST" , sum("Kennzahlen"."KOSTEN_TOTAL") as "KOSTEN_TOTAL" , sum("Kennzahlen"."LOHNSUMME") as "LOHNSUMME" , 
sum("Kennzahlen"."NETTOPRAEMIE") as "NETTOPRAEMIE" , sum("Kennzahlen"."PS") as "PS" , sum("Kennzahlen"."REGRESS_HK") 
as "REGRESS_HK" , sum("Kennzahlen"."REGRESS_INV") as "REGRESS_INV" , sum("Kennzahlen"."REGRESS_TG") as "REGRESS_TG" , 
sum("Kennzahlen"."REGRESS_TOD") as "REGRESS_TOD" , sum("Kennzahlen"."REGRESS_TOTAL") as "REGRESS_TOTAL" , 
sum("Kennzahlen"."RRU") as "RRU" , sum("Kennzahlen"."RS") as "RS" , sum("Kennzahlen"."RS_HK_TG") as "RS_HK_TG" , 
sum("Kennzahlen"."RST_HK") as "RST_HK" , sum("Kennzahlen"."RST_HK_BK") as "RST_HK_BK" , sum("Kennzahlen"."RST_HK_UNF") 
as "RST_HK_UNF" , sum("Kennzahlen"."RST_INV") as "RST_INV" , sum("Kennzahlen"."RST_TG") as "RST_TG" , 
sum("Kennzahlen"."RST_TG_BK") as "RST_TG_BK" , sum("Kennzahlen"."RST_TG_UNF") as "RST_TG_UNF" , sum("Kennzahlen"."RST_TOD") 
as "RST_TOD" , sum("Kennzahlen"."RST_TOTAL") as "RST_TOTAL" , sum("Kennzahlen"."RST_VERMUTET_RENTE_BK") as 
"RST_VERMUTET_RENTE_BK" , sum("Kennzahlen"."RST_VERMUTET_RENTE_UNF") as "RST_VERMUTET_RENTE_UNF" , sum("Kennzahlen"."RST_VR") 
as "RST_VR" , sum("Kennzahlen"."SANIERUNGSBETRAEGE") as "SANIERUNGSBETRAEGE" , sum("Kennzahlen"."SICH_ZUSCHLAG") as 
"SICH_ZUSCHLAG" , sum("Kennzahlen"."SOLIDARITAETSBEITRAG") as "SOLIDARITAETSBEITRAG" , sum("Kennzahlen"."STAND_AUSGLEICHSRESERVE") 
as "STAND_AUSGLEICHSRESERVE" , sum("Kennzahlen"."TG") as "TG" , sum("Kennzahlen"."TG_BK") as "TG_BK" , sum("Kennzahlen"."TG_UNF") 
as "TG_UNF" , sum("Kennzahlen"."TOD") as "TOD" , sum("Kennzahlen"."TOD_BK") as "TOD_BK" , sum("Kennzahlen"."TOD_UNF") as 
"TOD_UNF" , sum("Kennzahlen"."TODESFAELLE") as "TODESFAELLE" , sum("Kennzahlen"."TODESFAELLE_HR") as "TODESFAELLE_HR" , 
sum("Kennzahlen"."TU") as "TU" , sum("Kennzahlen"."VERMUTET_RENTE") as "VERMUTET_RENTE" , sum("Kennzahlen"."VOLLBESCHAEFTIGTE") 
as "VOLLBESCHAEFTIGTE" , sum("Kennzahlen"."ZUWEIS_ALLG_RS") as "ZUWEIS_ALLG_RS" , sum("Kennzahlen"."ZUWEISUNG_AN_RESERVEN") 
as "ZUWEISUNG_AN_RESERVEN" from "RIS_417"."Kennzahlen" "Kennzahlen" , "RIS_417"."Versicherungszweig" "Versicherungszweig", 
"RIS_417"."Statistikjahr" "Statistikjahr" , "RIS_417"."Unfalljahr" "Unfalljahr" , "RIS_417"."Praemienart" "Praemienart" , 
"RIS_417"."Klassierung - KLS" "Klassierung - KLS" , "RIS_417"."Taggeldaufschub" "Taggeldaufschub" where "Statistikjahr"."STAT_JAHR 
- JAHR_4_STELLIG" = :pv_SJ1 and ( cast("Unfalljahr"."UNF_JAHR - JAHR_4_STELLIG" as integer) >= 1918 and cast("Unfalljahr"."UNF_JAHR 
- JAHR_4_STELLIG" as integer) <= 1983 ) and :pv_ANZUJ >= 20 and "CODE_VZWG" = :pv_VZWG1 and "Praemienart"."ART_CODE" = :pv_PRMART1 
and "Klassierung - KLS"."KLS - RGM_CODE" in (:pv_KLS1) and "Taggeldaufschub"."CODE_TGAU - TG_AFS_CODE" in(:pv_TGAFS1) and 
"Kennzahlen"."ANZ_FAELLE_TOT" is not null and 'OFF' = :pv_MULTI group by "Klassierung - KLS"."KLS - RGM_CODE" union select 2 as a , 
'' as b_d , '' as b_f , '' as b_i , "Klassierung - KLS"."KLS - RGM_TYPE" as "RGM_TYPE" , "Klassierung - KLS"."KLS - RGM_CODE" as 
"RGM_CODE" , "Klassierung - KLS"."KLS - BEZ_D" as "RGM_BEZ_D" , "Klassierung - KLS"."KLS - BEZ_I" as "RGM_BEZ_I" , "Klassierung - 
KLS"."KLS - BEZ_F" as "RGM_BEZ_F" , "Versicherungszweig"."CODE_VZWG - VZW_I_KURZ_BEZ" as "VZW_KURZ_BEZ_I", 
"Versicherungszweig"."CODE_VZWG - VZW_F_KURZ_BEZ" as "VZW_KURZ_BEZ_F", "Versicherungszweig"."CODE_VZWG - VZW_D_KURZ_BEZ" as 
"VZW_KURZ_BEZ_D", "Praemienart"."ART_CODE - PRM_ART_CODE" as "PRM_ART_CODE" , concat('84-', substring((cast(max("Unfalljahr"."UNF_JAHR 
- JAHR_4_STELLIG") as char(4))) from 3)) as "UNF_JAHR" , "Statistikjahr"."STAT_JAHR - JAHR_4_STELLIG" as "STAT_JAHR" , 
"Versicherungszweig"."CODE_VZWG" as "CODE_VZWG" , sum("Kennzahlen"."ABBAU_DER_AUSGLEICHSRESERVE") as "ABBAU_DER_AUSGLEICHSRESERVE", 
sum("Kennzahlen"."AMORTISATIONSBETRAEGE") as "AMORTISATIONSBETRAEGE" , sum("Kennzahlen"."ANZ_BT_QUELLEN") as "ANZ_BT_QUELLEN" , 
sum("Kennzahlen"."ANZ_FAELLE_TOT") as "ANZ_FAELLE_TOT" , sum("Kennzahlen"."ANZ_UNF_PRO_MIO_LS") as "ANZ_UNF_PRO_MIO_LS" , 
sum("Kennzahlen"."ANZ_UNF_PRO_TSD_VB") as "ANZ_UNF_PRO_TSD_VB" , sum("Kennzahlen"."AUFWAND") as "AUFWAND" , 
sum("Kennzahlen"."AUFWAND_HK_TG") as "AUFWAND_HK_TG" , sum("Kennzahlen"."AUFWAND_HK_TG_ZU_TOTAL") as "AUFWAND_HK_TG_ZU_TOTAL" , 
sum("Kennzahlen"."AUFWAND_OHNE_ZWR") as "AUFWAND_OHNE_ZWR" , sum("Kennzahlen"."AUFWAND_PRO_UNF") as "AUFWAND_PRO_UNF" , 
sum("Kennzahlen"."EINMALZAHLUNG") as "EINMALZAHLUNG" , sum("Kennzahlen"."FAELLE_BAGATELL") as "FAELLE_BAGATELL" , 
sum("Kennzahlen"."FAELLE_IE") as "FAELLE_IE" , sum("Kennzahlen"."FAELLE_INV") as "FAELLE_INV" , sum("Kennzahlen"."FAELLE_IR") 
as "FAELLE_IR" , sum("Kennzahlen"."FAELLE_ORDENTLICH") as "FAELLE_ORDENTLICH" , sum("Kennzahlen"."FAELLE_TG") as "FAELLE_TG" , 
sum("Kennzahlen"."FAELLE_VERMUT_RENTE") as "FAELLE_VERMUT_RENTE" , sum("Kennzahlen"."GUTSCHRIFTEN") as "GUTSCHRIFTEN" , 
sum("Kennzahlen"."HK") as "HK" , sum("Kennzahlen"."HK_BK") as "HK_BK" , sum("Kennzahlen"."HK_UNF") as "HK_UNF" , 
sum("Kennzahlen"."IE") as "IE" , sum("Kennzahlen"."IE_BK") as "IE_BK" , sum("Kennzahlen"."IE_UNF") as "IE_UNF" , 
sum("Kennzahlen"."INV") as "INV" , sum("Kennzahlen"."INV_BK") as "INV_BK" , sum("Kennzahlen"."INV_UNF") as "INV_UNF" , 
sum("Kennzahlen"."KAPITALERTRAGSUEBERSCHUSS") as "KAPITALERTRAGSUEBERSCHUSS" , sum("Kennzahlen"."KOLLEK_BELAST") as 
"KOLLEK_BELAST" , sum("Kennzahlen"."KOSTEN_TOTAL") as "KOSTEN_TOTAL" , sum("Kennzahlen"."LOHNSUMME") as "LOHNSUMME" , 
sum("Kennzahlen"."NETTOPRAEMIE") as "NETTOPRAEMIE" , sum("Kennzahlen"."PS") as "PS" , sum("Kennzahlen"."REGRESS_HK") as 
"REGRESS_HK" , sum("Kennzahlen"."REGRESS_INV") as "REGRESS_INV" , sum("Kennzahlen"."REGRESS_TG") as "REGRESS_TG" , 
sum("Kennzahlen"."REGRESS_TOD") as "REGRESS_TOD" , sum("Kennzahlen"."REGRESS_TOTAL") as "REGRESS_TOTAL" , sum("Kennzahlen"."RRU") 
as "RRU" , sum("Kennzahlen"."RS") as "RS" , sum("Kennzahlen"."RS_HK_TG") as "RS_HK_TG" , sum("Kennzahlen"."RST_HK") as "RST_HK" , 
sum("Kennzahlen"."RST_HK_BK") as "RST_HK_BK" , sum("Kennzahlen"."RST_HK_UNF") as "RST_HK_UNF" , sum("Kennzahlen"."RST_INV") as 
"RST_INV" , sum("Kennzahlen"."RST_TG") as "RST_TG" , sum("Kennzahlen"."RST_TG_BK") as "RST_TG_BK" , sum("Kennzahlen"."RST_TG_UNF") 
as "RST_TG_UNF" , sum("Kennzahlen"."RST_TOD") as "RST_TOD" , sum("Kennzahlen"."RST_TOTAL") as "RST_TOTAL" , 
sum("Kennzahlen"."RST_VERMUTET_RENTE_BK") as "RST_VERMUTET_RENTE_BK" , sum("Kennzahlen"."RST_VERMUTET_RENTE_UNF") as 
"RST_VERMUTET_RENTE_UNF" , sum("Kennzahlen"."RST_VR") as "RST_VR" , sum("Kennzahlen"."SANIERUNGSBETRAEGE") as "SANIERUNGSBETRAEGE" , 
sum("Kennzahlen"."SICH_ZUSCHLAG") as "SICH_ZUSCHLAG" , sum("Kennzahlen"."SOLIDARITAETSBEITRAG") as "SOLIDARITAETSBEITRAG" , 
sum("Kennzahlen"."STAND_AUSGLEICHSRESERVE") as "STAND_AUSGLEICHSRESERVE" , sum("Kennzahlen"."TG") as "TG" , 
sum("Kennzahlen"."TG_BK") as "TG_BK" , sum("Kennzahlen"."TG_UNF") as "TG_UNF" , sum("Kennzahlen"."TOD") as "TOD" , 
sum("Kennzahlen"."TOD_BK") as "TOD_BK" , sum("Kennzahlen"."TOD_UNF") as "TOD_UNF" , sum("Kennzahlen"."TODESFAELLE") as 
"TODESFAELLE" , sum("Kennzahlen"."TODESFAELLE_HR") as "TODESFAELLE_HR" , sum("Kennzahlen"."TU") as "TU" , 
sum("Kennzahlen"."VERMUTET_RENTE") as "VERMUTET_RENTE" , sum("Kennzahlen"."VOLLBESCHAEFTIGTE") as "VOLLBESCHAEFTIGTE" , 
sum("Kennzahlen"."ZUWEIS_ALLG_RS") as "ZUWEIS_ALLG_RS" , sum("Kennzahlen"."ZUWEISUNG_AN_RESERVEN") as "ZUWEISUNG_AN_RESERVEN" 
from "RIS_417"."Kennzahlen" "Kennzahlen" , "RIS_417"."Versicherungszweig" "Versicherungszweig", "RIS_417"."Statistikjahr" 
"Statistikjahr" , "RIS_417"."Unfalljahr" "Unfalljahr" , "RIS_417"."Praemienart" "Praemienart" , "RIS_417"."Klassierung - KLS" 
"Klassierung - KLS" , "RIS_417"."Taggeldaufschub" "Taggeldaufschub" where "Statistikjahr"."STAT_JAHR - JAHR_4_STELLIG" = 
:pv_SJ1 and ( cast("Unfalljahr"."UNF_JAHR - JAHR_4_STELLIG" as integer) >= 1984 and cast("Unfalljahr"."UNF_JAHR - 
JAHR_4_STELLIG" as integer) <= (ifnull(:pv_FROM_UJ1, 0)-1)) and :pv_ANZUJ >= 20 and "Versicherungszweig"."CODE_VZWG" = 
:pv_VZWG1 and "Praemienart"."ART_CODE" = :pv_PRMART1 and "Klassierung - KLS"."KLS - RGM_CODE" in (:pv_KLS1) and 
"Taggeldaufschub"."CODE_TGAU - TG_AFS_CODE" in(:pv_TGAFS1) and "Kennzahlen"."ANZ_FAELLE_TOT" is not null and 'OFF' = 
:pv_MULTI group by "Klassierung - KLS"."KLS - RGM_CODE" union select 1 as a , '' as b_d , '' as b_f , '' as b_i , 
max("Klassierung - KLS"."KLS - RGM_TYPE") as "RGM_TYPE" , 'RGM' as "RGM_CODE" , '' as "RGM_BEZ_D" , '' as "RGM_BEZ_I" , 
'' as "RGM_BEZ_F" , max("Versicherungszweig"."CODE_VZWG - VZW_I_KURZ_BEZ") as "VZW_KURZ_BEZ_I" , 
max("Versicherungszweig"."CODE_VZWG - VZW_F_KURZ_BEZ") as "VZW_KURZ_BEZ_F" , max("Versicherungszweig"."CODE_VZWG - 
VZW_D_KURZ_BEZ") as "VZW_KURZ_BEZ_D" , max("Praemienart"."ART_CODE - PRM_ART_CODE") as "PRM_ART_CODE" , '18-83' as "UNF_JAHR" , 
max("Statistikjahr"."STAT_JAHR - JAHR_4_STELLIG") as "STAT_JAHR" , max("Versicherungszweig"."CODE_VZWG") as "CODE_VZWG" , 
sum("Kennzahlen"."ABBAU_DER_AUSGLEICHSRESERVE") as "ABBAU_DER_AUSGLEICHSRESERVE", sum("Kennzahlen"."AMORTISATIONSBETRAEGE") as 
"AMORTISATIONSBETRAEGE" , sum("Kennzahlen"."ANZ_BT_QUELLEN") as "ANZ_BT_QUELLEN" , sum("Kennzahlen"."ANZ_FAELLE_TOT") as 
"ANZ_FAELLE_TOT" , sum("Kennzahlen"."ANZ_UNF_PRO_MIO_LS") as "ANZ_UNF_PRO_MIO_LS" , sum("Kennzahlen"."ANZ_UNF_PRO_TSD_VB") 
as "ANZ_UNF_PRO_TSD_VB" , sum("Kennzahlen"."AUFWAND") as "AUFWAND" , sum("Kennzahlen"."AUFWAND_HK_TG") as "AUFWAND_HK_TG" , 
sum("Kennzahlen"."AUFWAND_HK_TG_ZU_TOTAL") as "AUFWAND_HK_TG_ZU_TOTAL" , sum("Kennzahlen"."AUFWAND_OHNE_ZWR") as 
"AUFWAND_OHNE_ZWR" , sum("Kennzahlen"."AUFWAND_PRO_UNF") as "AUFWAND_PRO_UNF" , sum("Kennzahlen"."EINMALZAHLUNG") as 
"EINMALZAHLUNG" , sum("Kennzahlen"."FAELLE_BAGATELL") as "FAELLE_BAGATELL" , sum("Kennzahlen"."FAELLE_IE") as "FAELLE_IE" , 
sum("Kennzahlen"."FAELLE_INV") as "FAELLE_INV" , sum("Kennzahlen"."FAELLE_IR") as "FAELLE_IR" , sum("Kennzahlen"."FAELLE_ORDENTLICH") 
as "FAELLE_ORDENTLICH" , sum("Kennzahlen"."FAELLE_TG") as "FAELLE_TG" , sum("Kennzahlen"."FAELLE_VERMUT_RENTE") as 
"FAELLE_VERMUT_RENTE" , sum("Kennzahlen"."GUTSCHRIFTEN") as "GUTSCHRIFTEN" , sum("Kennzahlen"."HK") as "HK" , 
sum("Kennzahlen"."HK_BK") as "HK_BK" , sum("Kennzahlen"."HK_UNF") as "HK_UNF" , sum("Kennzahlen"."IE") as "IE" , 
sum("Kennzahlen"."IE_BK") as "IE_BK" , sum("Kennzahlen"."IE_UNF") as "IE_UNF" , sum("Kennzahlen"."INV") as "INV" , 
sum("Kennzahlen"."INV_BK") as "INV_BK" , sum("Kennzahlen"."INV_UNF") as "INV_UNF" , sum("Kennzahlen"."KAPITALERTRAGSUEBERSCHUSS") 
as "KAPITALERTRAGSUEBERSCHUSS" , sum("Kennzahlen"."KOLLEK_BELAST") as "KOLLEK_BELAST" , sum("Kennzahlen"."KOSTEN_TOTAL") 
as "KOSTEN_TOTAL" , sum("Kennzahlen"."LOHNSUMME") as "LOHNSUMME" , sum("Kennzahlen"."NETTOPRAEMIE") as "NETTOPRAEMIE" , 
sum("Kennzahlen"."PS") as "PS" , sum("Kennzahlen"."REGRESS_HK") as "REGRESS_HK" , sum("Kennzahlen"."REGRESS_INV") as "REGRESS_INV" , 
sum("Kennzahlen"."REGRESS_TG") as "REGRESS_TG" , sum("Kennzahlen"."REGRESS_TOD") as "REGRESS_TOD" , sum("Kennzahlen"."REGRESS_TOTAL") 
as "REGRESS_TOTAL" , sum("Kennzahlen"."RRU") as "RRU" , sum("Kennzahlen"."RS") as "RS" , sum("Kennzahlen"."RS_HK_TG") as "RS_HK_TG" , 
sum("Kennzahlen"."RST_HK") as "RST_HK" , sum("Kennzahlen"."RST_HK_BK") as "RST_HK_BK" , sum("Kennzahlen"."RST_HK_UNF") as 
"RST_HK_UNF" , sum("Kennzahlen"."RST_INV") as "RST_INV" , sum("Kennzahlen"."RST_TG") as "RST_TG" , sum("Kennzahlen"."RST_TG_BK") 
as "RST_TG_BK" , sum("Kennzahlen"."RST_TG_UNF") as "RST_TG_UNF" , sum("Kennzahlen"."RST_TOD") as "RST_TOD" , 
sum("Kennzahlen"."RST_TOTAL") as "RST_TOTAL" , sum("Kennzahlen"."RST_VERMUTET_RENTE_BK") as "RST_VERMUTET_RENTE_BK" , 
sum("Kennzahlen"."RST_VERMUTET_RENTE_UNF") as "RST_VERMUTET_RENTE_UNF" , sum("Kennzahlen"."RST_VR") as "RST_VR" , 
sum("Kennzahlen"."SANIERUNGSBETRAEGE") as "SANIERUNGSBETRAEGE" , sum("Kennzahlen"."SICH_ZUSCHLAG") as "SICH_ZUSCHLAG" , 
sum("Kennzahlen"."SOLIDARITAETSBEITRAG") as "SOLIDARITAETSBEITRAG" , sum("Kennzahlen"."STAND_AUSGLEICHSRESERVE") as 
"STAND_AUSGLEICHSRESERVE" , sum("Kennzahlen"."TG") as "TG" , sum("Kennzahlen"."TG_BK") as "TG_BK" , sum("Kennzahlen"."TG_UNF") 
as "TG_UNF" , sum("Kennzahlen"."TOD") as "TOD" , sum("Kennzahlen"."TOD_BK") as "TOD_BK" , sum("Kennzahlen"."TOD_UNF") as "TOD_UNF" , 
sum("Kennzahlen"."TODESFAELLE") as "TODESFAELLE" , sum("Kennzahlen"."TODESFAELLE_HR") as "TODESFAELLE_HR" , sum("Kennzahlen"."TU") 
as "TU" , sum("Kennzahlen"."VERMUTET_RENTE") as "VERMUTET_RENTE" , sum("Kennzahlen"."VOLLBESCHAEFTIGTE") as "VOLLBESCHAEFTIGTE" , 
sum("Kennzahlen"."ZUWEIS_ALLG_RS") as "ZUWEIS_ALLG_RS" , sum("Kennzahlen"."ZUWEISUNG_AN_RESERVEN") as "ZUWEISUNG_AN_RESERVEN" from 
"RIS_417"."Kennzahlen" "Kennzahlen" , "RIS_417"."Versicherungszweig" "Versicherungszweig", "RIS_417"."Statistikjahr" 
"Statistikjahr" , "RIS_417"."Unfalljahr" "Unfalljahr" , "RIS_417"."Praemienart" "Praemienart" , "RIS_417"."Klassierung - 
KLS" "Klassierung - KLS" , "RIS_417"."Taggeldaufschub" "Taggeldaufschub" where "Statistikjahr"."STAT_JAHR - JAHR_4_STELLIG" 
= :pv_SJ1 and ( cast("Unfalljahr"."UNF_JAHR - JAHR_4_STELLIG" as integer) >= 1918 and cast("Unfalljahr"."UNF_JAHR - 
JAHR_4_STELLIG" as integer) <= 1983) and :pv_ANZUJ >= 20 and "Versicherungszweig"."CODE_VZWG" = :pv_VZWG1 and 
"Praemienart"."ART_CODE" = :pv_PRMART1 and "Klassierung - KLS"."KLS - RGM_CODE" in (:pv_KLS1) and "Taggeldaufschub"."CODE_TGAU 
- TG_AFS_CODE" in(:pv_TGAFS1) and "Kennzahlen"."ANZ_FAELLE_TOT" is not null and 'ON' = :pv_MULTI union select 2 as a , '' 
as b_d , '' as b_f , '' as b_i , max("Klassierung - KLS"."KLS - RGM_TYPE") as "RGM_TYPE" , 'RGM' as "RGM_CODE" , '' as 
"RGM_BEZ_D" , '' as "RGM_BEZ_I" , '' as "RGM_BEZ_F" , max("Versicherungszweig"."CODE_VZWG - VZW_I_KURZ_BEZ") as "VZW_KURZ_BEZ_I", 
max("Versicherungszweig"."CODE_VZWG - VZW_F_KURZ_BEZ") as "VZW_KURZ_BEZ_F", max("Versicherungszweig"."CODE_VZWG - 
VZW_D_KURZ_BEZ") as "VZW_KURZ_BEZ_D", max("Praemienart"."ART_CODE - PRM_ART_CODE") as "PRM_ART_CODE" , concat('84-', 
substring((cast(max("Unfalljahr"."UNF_JAHR - JAHR_4_STELLIG") as char(4))) from 3)) as "UNF_JAHR" , max("Statistikjahr"."STAT_JAHR 
- JAHR_4_STELLIG") as "STAT_JAHR" , max("Versicherungszweig"."CODE_VZWG") as "CODE_VZWG" , sum("Kennzahlen"."ABBAU_DER_AUSGLEICHSRESERVE") 
as "ABBAU_DER_AUSGLEICHSRESERVE", sum("Kennzahlen"."AMORTISATIONSBETRAEGE") as "AMORTISATIONSBETRAEGE" , sum("Kennzahlen"."ANZ_BT_QUELLEN") 
as "ANZ_BT_QUELLEN" , sum("Kennzahlen"."ANZ_FAELLE_TOT") as "ANZ_FAELLE_TOT" , sum("Kennzahlen"."ANZ_UNF_PRO_MIO_LS") as 
"ANZ_UNF_PRO_MIO_LS" , sum("Kennzahlen"."ANZ_UNF_PRO_TSD_VB") as "ANZ_UNF_PRO_TSD_VB" , sum("Kennzahlen"."AUFWAND") as 
"AUFWAND" , sum("Kennzahlen"."AUFWAND_HK_TG") as "AUFWAND_HK_TG" , sum("Kennzahlen"."AUFWAND_HK_TG_ZU_TOTAL") as 
"AUFWAND_HK_TG_ZU_TOTAL" , sum("Kennzahlen"."AUFWAND_OHNE_ZWR") as "AUFWAND_OHNE_ZWR" , sum("Kennzahlen"."AUFWAND_PRO_UNF") 
as "AUFWAND_PRO_UNF" , sum("Kennzahlen"."EINMALZAHLUNG") as "EINMALZAHLUNG" , sum("Kennzahlen"."FAELLE_BAGATELL") as "FAELLE_BAGATELL" , 
sum("Kennzahlen"."FAELLE_IE") as "FAELLE_IE" , sum("Kennzahlen"."FAELLE_INV") as "FAELLE_INV" , sum("Kennzahlen"."FAELLE_IR") as 
"FAELLE_IR" , sum("Kennzahlen"."FAELLE_ORDENTLICH") as "FAELLE_ORDENTLICH" , sum("Kennzahlen"."FAELLE_TG") as "FAELLE_TG" , 
sum("Kennzahlen"."FAELLE_VERMUT_RENTE") as "FAELLE_VERMUT_RENTE" , sum("Kennzahlen"."GUTSCHRIFTEN") as "GUTSCHRIFTEN" , 
sum("Kennzahlen"."HK") as "HK" , sum("Kennzahlen"."HK_BK") as "HK_BK" , sum("Kennzahlen"."HK_UNF") as "HK_UNF" , 
sum("Kennzahlen"."IE") as "IE" , sum("Kennzahlen"."IE_BK") as "IE_BK" , sum("Kennzahlen"."IE_UNF") as "IE_UNF" , 
sum("Kennzahlen"."INV") as "INV" , sum("Kennzahlen"."INV_BK") as "INV_BK" , sum("Kennzahlen"."INV_UNF") as "INV_UNF" , 
sum("Kennzahlen"."KAPITALERTRAGSUEBERSCHUSS") as "KAPITALERTRAGSUEBERSCHUSS" , sum("Kennzahlen"."KOLLEK_BELAST") as 
"KOLLEK_BELAST" , sum("Kennzahlen"."KOSTEN_TOTAL") as "KOSTEN_TOTAL" , sum("Kennzahlen"."LOHNSUMME") as "LOHNSUMME" , 
sum("Kennzahlen"."NETTOPRAEMIE") as "NETTOPRAEMIE" , sum("Kennzahlen"."PS") as "PS" , sum("Kennzahlen"."REGRESS_HK") as 
"REGRESS_HK" , sum("Kennzahlen"."REGRESS_INV") as "REGRESS_INV" , sum("Kennzahlen"."REGRESS_TG") as "REGRESS_TG" , 
sum("Kennzahlen"."REGRESS_TOD") as "REGRESS_TOD" , sum("Kennzahlen"."REGRESS_TOTAL") as "REGRESS_TOTAL" , sum("Kennzahlen"."RRU") 
as "RRU" , sum("Kennzahlen"."RS") as "RS" , sum("Kennzahlen"."RS_HK_TG") as "RS_HK_TG" , sum("Kennzahlen"."RST_HK") as "RST_HK" , 
sum("Kennzahlen"."RST_HK_BK") as "RST_HK_BK" , sum("Kennzahlen"."RST_HK_UNF") as "RST_HK_UNF" , sum("Kennzahlen"."RST_INV") as 
"RST_INV" , sum("Kennzahlen"."RST_TG") as "RST_TG" , sum("Kennzahlen"."RST_TG_BK") as "RST_TG_BK" , sum("Kennzahlen"."RST_TG_UNF") 
as "RST_TG_UNF" , sum("Kennzahlen"."RST_TOD") as "RST_TOD" , sum("Kennzahlen"."RST_TOTAL") as "RST_TOTAL" , 
um("Kennzahlen"."RST_VERMUTET_RENTE_BK") as "RST_VERMUTET_RENTE_BK" , sum("Kennzahlen"."RST_VERMUTET_RENTE_UNF") as 
"RST_VERMUTET_RENTE_UNF" , sum("Kennzahlen"."RST_VR") as "RST_VR" , sum("Kennzahlen"."SANIERUNGSBETRAEGE") as "SANIERUNGSBETRAEGE" , 
sum("Kennzahlen"."SICH_ZUSCHLAG") as "SICH_ZUSCHLAG" , sum("Kennzahlen"."SOLIDARITAETSBEITRAG") as "SOLIDARITAETSBEITRAG" , 
sum("Kennzahlen"."STAND_AUSGLEICHSRESERVE") as "STAND_AUSGLEICHSRESE