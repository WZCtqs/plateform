package com.szhbl.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 集疏备注内容切换
 * @Author: shy
 * @Date: 2020/6/19
 */
@Getter
@ToString
public enum CadNoteEnum {

    去程整柜转关("去程整柜转关","1.The offer covers the charge for T1 document（only include general cargoes value, If the cargo value is above $100000, T1 charge will increase, the T1 cost should be re-counted). The T1 cost only covers three HS codes,(Only one HS code listed on the packing list and commercial invoice, but customs office issued ATB documents which showed many positions. All costs should be re-counted, 10EUR per position)； \n" +
            "2.EORI number and VAT number are registered by Chinese companies and personal, please inform ZIH in advance in order to confirm the operation possibility.\n" +
            "3. For non-ATA special customs services, such as maintenance product clearance, please inquire for operability;\n" +
            "4..Consignee should do customs clearance at destination,  the charge need to be re-counted;（Free waiting time is 1 hour, otherwise 40 Euro every half an hour when exceed should be charged）\n" +
            "5..Free unloadinging time is 2 hours, otherwise 40 Euro every half an hour when exceed should be charged;\n" +
            "6.please noted that: Cargo weight limit: 21Ton/40HQ/40GP, 20Ton/20HQ/20GP/40HOT,19Ton/20HOT,18Ton45RF/40RF;\n" +
            "7.The offer is available for using normal truck, please inform us in advance if you have special requirements on transport time and transport vehicle;\n" +
            "8.The offer is available for general cargo, please inform us specially on inquiry if cargo is balance scooter or other dangerous and special cargo;(for Amazon cargo, plesae inquire for operability);\n" +
            "9.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    去程散货转关("去程散货转关","天津自贸试验区（天津港保税区）海滨大道3360号，（原塘沽区临港路）港外博达堆场"),

    回程整柜报关("回程整柜报关","1.the offer exclude export declaration, if needed, 100EUR per shipment/time( inculding 3HS code, 10 EUR/HS code if exceed. If the information provided by shipper is incorrect and the second customs declaration is required, the second customs declaration fee of 100 euros shall be borne by the shipper or the customer. if export declaration done by shipper, 100EUR will be charged for communicating documenrs issues with shipper.\n" +
            "2.if  export declaration entrust to ZIH, documents should be provided by shipper at least 24 hours before delivery. if export declaration done by shipper, ABD docs. should be provided 3 wordking days  before train departure.\n" +
            "3.EORI number and VAT number are registered by Chinese companies and personal, please inform ZIH in advance in order to confirm  the operation possibility.\n" +
            "4.Free loadinging time is 2 hours, otherwise 40 Euro every half an hour when exceed should be charged;\n" +
            "5.container seal should be stall by shipper, 15EUR/seal will be charged if need shipper buy. 19 seals needed for 40HOT, 9/11seals needed for 20HOT.\n" +
            "6.The offer is available for using normal truck, please inform us in advance if you have special requirements on transport time and transport vehicle.\n" +
            "7.The offer is available for general cargo, please inform us specially on inquiry if cargo is balance scooter or other dangerous and special cargo.\n" +
            "8.please noted that: Cargo weight limit: 21Ton/40HQ/40GP, 20Ton/20HQ/20GP/40HOT,19Ton/20HOT,18Ton45RF/40RF;\n" +
            "9.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    回程散货报关("回程散货报关","1.the offer exclude export declaration, if needed, 100EUR per shipment/time( inculding 3HS code, 10 EUR/HS code if exceed. If the information provided by shipper is incorrect and the second customs declaration is required, the second customs declaration fee of 100 euros shall be borne by the shipper or the customer. if export declaration done by shipper, 100EUR will be charged for communicating documenrs issues with shipper.\n" +
            "2.if  export declaration entrust to ZIH, documents should be provided by shipper at least 24 hours before delivery. if export declaration done by shipper, ABD docs. should be provided 3 wordking days  before train departure.\n" +
            "3.Free loadinging time is 2 hours, otherwise 40 Euro every half an hour when exceed should be charged;\n" +
            "4.If cargo value high enough, please inform us in advance or client purchase European road transportation insurance. If Client doesn’t inform us cargo value in advance and doesn’t purchase European road transportation insurance, cargo were damaged on transportation, Client should bear the results by themselves;.\n" +
            "5.The offer is available for using normal truck, please inform us in advance if you have special requirements on transport time and transport vehicle.\n" +
            "6.The offer is available for general cargo, please inform us specially on inquiry if cargo is balance scooter or other dangerous and special cargo.\n" +
            "7.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    回程非欧盟整柜转关("回程非欧盟整柜转关","1.The offer covers the charge for T1 document（only include general cargoes value, If the cargo value is above $100000, T1 charge will increase, the T1 cost should be re-counted).  if  export declaration entrust to ZIH, 100EUR per shipment. if export declaration done by shipper, 100EUR will be charged for communicating documenrs issues with shipper.\n" +
            "2.if  export declaration entrust to ZIH, documents should be provided by shipper at least 24 hours before delivery. if export declaration done by shipper, ABD docs. should be provided 3 wordking days  before train departure.\n" +
            "3.EORI number and VAT number are registered by Chinese companies and personal, please inform ZIH in advance in order to confirm  the operation possibility.\n" +
            "4.Free loadinging time is 2 hours, otherwise 40 Euro every half an hour when exceed should be charged;\n" +
            "5.container seal should be stall by shipper, 15EUR/seal will be charged if need shipper buy. 19 seals needed for 40HOT, 9/11seals needed for 20HOT.\n" +
            "6.The offer is available for using normal truck, please inform us in advance if you have special requirements on transport time and transport vehicle.\n" +
            "7.The offer is available for general cargo, please inform us specially on inquiry if cargo is balance scooter or other dangerous and special cargo.\n" +
            "8.please noted that: Cargo weight limit: 21Ton/40HQ/40GP, 20Ton/20HQ/20GP/40HOT,19Ton/20HOT,18Ton45RF/40RF;\n" +
            "9.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    回程非欧盟散货转关("回程非欧盟散货转关","1.The offer covers the charge for T1 document（only include general cargoes value, If the cargo value is above $50000, T1 charge will increase, the T1 cost should be re-counted).  if  export declaration entrust to ZIH, 100EUR per shipment. if export declaration done by shipper, 100EUR will be charged for communicating documenrs issues with shipper.\n" +
            "2.if  export declaration entrust to ZIH, documents should be provided by shipper at least 24 hours before delivery. if export declaration done by shipper, ABD docs. should be provided 3 wordking days  before train departure.\n" +
            "3.Free loadinging time is 2 hours, otherwise 40 Euro every half an hour when exceed should be charged;\n" +
            "4.If cargo value high enough, please inform us in advance or client purchase European road transportation insurance. If Client doesn’t inform us cargo value in advance and doesn’t purchase European road transportation insurance, cargo were damaged on transportation, Client should bear the results by themselves;.\n" +
            "5.The offer is available for using normal truck, please inform us in advance if you have special requirements on transport time and transport vehicle.\n" +
            "6.The offer is available for general cargo, please inform us specially on inquiry if cargo is balance scooter or other dangerous and special cargo.\n" +
            "7.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    俄线去程整柜("俄线去程整柜","1.Free unloadinging time is 4 hours, otherwise 30 Euro every  hour when exceed should be charged.\n" +
            "2.The offer covers the charge for T1 document, inculding 3HS code,5EUR per HS code if exceed.\n" +
            "3. weight limit: less than 23ton (cargo+container weight).\n" +
            "4.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    俄线去程散货("俄线去程散货","1.Free unloadinging time is 4 hours, otherwise 30 Euro every  hour when exceed should be charged.\n" +
            "2.The offer covers the charge for T1 document, inculding 3HS code,5EUR per HS code if exceed.\n" +
            "3.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    俄线回程整柜("俄线回程整柜","1.Free loadinging time is 4 hours, otherwise 30 Euro every  hour when exceed should be charged.\n" +
            "2.export declaration excluded, should be done by shipper.\n" +
            "3. weight limit: less than 23ton (cargo+container weight).\n" +
            "4.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    俄线回程散货("俄线回程散货","1.Free loadinging time is 4 hours, otherwise 30 Euro every  hour when exceed should be charged.\n" +
            "2.export declaration excluded, should be done by shipper.\n" +
            "3. cargo transported on stackable.\n" +
            "4.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    莫斯科直达整柜("莫斯科直达整柜","1.Free unloadinging time is 4 hours, otherwise 30 Euro every  hour when exceed should be charged.\n" +
            "2.the offer exclude customs charge, cnee need do customs clearance at arrival station.\n" +
            "3.the offer inculding terminal handling charge and 2days free storage.\n" +
            "4.weight limit: less than 23ton (cargo+container weight).\n" +
            "5.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;\n" +
            "6.please give correct delivery information for changing Rialway bill in order to pick up container smoothly at least 3days before train departure."),

    莫斯科直达散货("莫斯科直达散货","1.the offer exclude customs charge, cnee need do customs clearance at arrival station.\n" +
            "2.Free unloadinging time is 4 hours, otherwise 30 Euro every  hour when exceed should be charged.\n" +
            "3.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;"),

    中亚去程整柜("中亚去程整柜","1.the offer exclude customs charge, cnee need do customs clearance at arrival station.\n" +
            "2.Free unloadinging time is 6 hours, otherwise 150USD every  day when exceed should be charged.\n" +
            "3. the offer inculding terminal handling charge.\n" +
            "4.weight limit: less than 20ton (cargo+container weight).\n" +
            "5.client should purchase road transportation insurance by themselves.\n" +
            "6.please give correct delivery information for changing Rialway bill in order to pick up container smoothly at least 3days before train departure.\n" +
            "7.Due to the impact of foreign epidemics(covid-19), the price has no validity time, and the vehicle condition must be consulted according to the local epidemic situation before delivery;");


    private String note;

    private String noteDetail;

    CadNoteEnum(String note, String noteDetail){
        this.note = note;
        this.noteDetail = noteDetail;
    }

    public static String getNoteDetail(String note) {
        CadNoteEnum[] cadNoteEnums = values();
        for (CadNoteEnum cadNoteEnum : cadNoteEnums) {
            if (cadNoteEnum.getNote().equals(note)) {
                return cadNoteEnum.getNoteDetail();
            }
        }
        return null;
    }

}
