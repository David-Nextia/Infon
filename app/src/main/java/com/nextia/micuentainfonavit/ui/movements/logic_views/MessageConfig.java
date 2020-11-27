package com.nextia.micuentainfonavit.ui.movements.logic_views;
/**
 * Class to generate the messages of the case types from the response object
 */
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.nextia.domain.models.saldo_movimientos.RespuestasDoMovs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MessageConfig {

    /*Variables
    * jsonColumnId = key id in json jsonMessageDictionary
    * jsonColumnDescription = key description in json jsonMessageDictionary
    * jsonColumnFields = key list fields in json jsonMessageDictionary
    * listValuesKeyJson = temporary list to search for key in json
    * */
    private static String jsonColumnId = "IdMessage";
    private static String jsonColumnDescription = "Description";
    private static String jsonColumnFields = "Fields";
    static List<String> listValuesKeyJson = new ArrayList<String>();

    //Primary method to get case type messages
    public static String buildMessage(RespuestasDoMovs respuestasDoMovs) {
        try {
            String message_return = "";

            JSONArray mJsonArray = new JSONArray(jsonMessageDictionary);
            //The character "|" is replaced so as not to have problems in the split
            String messages_list = respuestasDoMovs.getSalidagrals() != null ? respuestasDoMovs.getSalidagrals().getMensaje().trim().replace("|", "%") : "";
            if (messages_list != null && !messages_list.isEmpty()) {
                String[] parts = messages_list.split("%");
                for (String message : parts) {
                    JSONObject mJsonObjectMessage = getMessageById(message.trim(), mJsonArray);
                    if (mJsonObjectMessage != null) {
                        //It is validated if the object contains a list of fields to search within the response json
                        if (mJsonObjectMessage.has(jsonColumnFields)) {
                            JSONArray mJsonArrayField = mJsonObjectMessage.getJSONArray(jsonColumnFields);
                            String messageString = mJsonObjectMessage.getString(jsonColumnDescription);

                            //The list of fields is traversed to find the value of each field in the response json
                            for (int x = 0; x < mJsonArrayField.length(); x++) {
                                String field = mJsonArrayField.getString(x);
                                messageString = messageString.replace("%c" + (x + 1), getValueByKey(respuestasDoMovs, field));
                            }

                            message_return = message_return + (message_return.isEmpty() ? "" : "\n") + messageString;

                        } else {
                            //In case of not having fields, the description value is appended as it is in the json
                            message_return = message_return + (message_return.isEmpty() ? "" : "\n") + mJsonObjectMessage.getString(jsonColumnDescription);
                        }
                    }
                }
            }
            return message_return;
        } catch (Exception e) {
            return "";
        }
    }

    //Method to get a JSONObject from a JSONArray from the key
    private static JSONObject getMessageById(String id, JSONArray mJsonArray) throws JSONException {
        for (int x = 0; x < mJsonArray.length(); x++) {
            JSONObject mJsonObject = mJsonArray.getJSONObject(x);
            if (mJsonObject.getString(jsonColumnId).equals(id)) {
                return mJsonObject;
            }
        }
        return null;
    }

    //Main method to get the value of a key inside a response object
    private static String getValueByKey(RespuestasDoMovs respuestasDoMovs, String key) throws JSONException {
        //Object is serialized to a JsonElement for manipulation
        Gson mGson = new Gson();
        JsonElement mJsonElement = mGson.toJsonTree(respuestasDoMovs);

        //Method is executed to fill list with values ​​that match the key
        checkRecursiveJSON(key, mJsonElement);
        String value = (listValuesKeyJson != null && listValuesKeyJson.size() > 0 ? listValuesKeyJson.get(0) : "");

        //List is reset for next key search
        listValuesKeyJson.clear();

        //If the key is of type date, the string is formatted to the format dd.MM.yyyy
        if (key.toUpperCase().contains("FECHA")) {
            SimpleDateFormat spf = new SimpleDateFormat("yyyyMMdd");
            Date newDate = null;
            try {
                newDate = spf.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd.MM.yyyy");
            value = spf.format(newDate);
        }

        return value;
    }

    //Method to search the field recursively
    private static void checkRecursiveJSON(String key, JsonElement jsonElement) {
        //The jsonElement is an object is validated
        if (jsonElement.isJsonArray()) {
            for (JsonElement jsonElement1 : jsonElement.getAsJsonArray()) {
                checkRecursiveJSON(key, jsonElement1);
            }
        } else {
            //If it is not an object, it is validated if it is an array and the list of keys it contains is traversed through a HashMap
            if (jsonElement.isJsonObject()) {
                Set<Map.Entry<String, JsonElement>> entrySet = jsonElement.getAsJsonObject().entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    String key1 = entry.getKey();
                    if (key1.equals(key)) {
                        listValuesKeyJson.add(entry.getValue().getAsString().trim());
                    }
                    checkRecursiveJSON(key, entry.getValue());
                }
            } else {
                //Otherwise, it is validated if the element matches the field
                if (jsonElement.toString().equals(key)) {
                    listValuesKeyJson.add(jsonElement.getAsString().trim());
                }
            }
        }
    }

    //Message dictionary in JSON format
    private static String jsonMessageDictionary = "[\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"01\",\n" +
            "\t\t\"Description\": \"El crédito %c1 se liquidó el día  %c2, a través de %c3.\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v1TipoCredito\",\n" +
            "\t\t\t\"v2FechaLiquidacion\",\n" +
            "\t\t\t\"v3TipoLiquidacion\"\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"02\",\n" +
            "\t\t\"Description\": \"El crédito %c1 se cerró el día  %c2.\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v1TipoCredito\",\n" +
            "\t\t\t\"v2FechaLiquidacion\"\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"03\",\n" +
            "\t\t\"Description\": \"Si quieres saber si tu crédito tiene pagos en exceso consulta la información en Mi Cuenta Infonavit.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"04\",\n" +
            "\t\t\"Description\": \"El crédito tipo %c1 tiene la situación de %c2\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v1TipoCredito\",\n" +
            "\t\t\t\"v4DescLegalStatus\"\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"05\",\n" +
            "\t\t\"Description\": \"Tu crédito se encuentra en prórroga especial y durante el periodo que abarca no se exige el pago de tu mensualidad. Recuerda realizar tus pagos al término de este apoyo para que tu deuda no se incremente.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"06\",\n" +
            "\t\t\"Description\": \"Por la falta de pago de tu crédito, se tendrá la necesidad de iniciar un proceso de demanda. Comunícate al 01800 685 8836 para proporcionarte toda la información sobre tu crédito y las diferentes alternativas que el Infonavit tiene para regularizar tu situación.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"07\",\n" +
            "\t\t\"Description\": \"Por el incumplimiento de los pagos de tu crédito hipotecario, este se encuentra EN PROCESO JURIDICO y fue asignado al despacho de cobranza %c1. Si buscas una solución, acércate al despacho que te contactó, o si lo prefieres, acude a la gerencia de cobranza de la delegación del Infonavit más cercana.\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v5DespachoCobranza\"\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"08\",\n" +
            "\t\t\"Description\": \"Información actualizada al %c1.\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v9FechaAsignacion\"\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"09\",\n" +
            "\t\t\"Description\": \"Tu crédito se encuentra asignado el despacho de cobranza %c1 y en los próximos días te estarán visitando para ofrecerte una solución de pago que te permita conservar tu vivienda.\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v5DespachoCobranza\"\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"10\",\n" +
            "\t\t\"Description\": \"Cuentas con un convenio judicial desde %c1. Mantén al corriente  tus pagos para que no se reactive el proceso judicial.\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v7FechaReestructura\"\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"11\",\n" +
            "\t\t\"Description\": \"Cuentas con una reestructura desde %c1.\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v7FechaReestructura\"\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"12\",\n" +
            "\t\t\"Description\": \"Te recordamos que tienes disponible tu fondo de protección de pagos, a través del cual sólo debes pagar una parte de tu mensualidad y el resto será abonado a tu crédito.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"13\",\n" +
            "\t\t\"Description\": \"Actualmente tu crédito cuenta con una prórroga y no se exige el pago de tu mensualidad, pero los intereses, seguros y cuotas se acumulan mes a mes a tu saldo por lo que se extenderá el plazo para liquidar tu crédito.  Si decides pagar, se suspenderá la prórroga y deberás pagar mes a mes.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"14\",\n" +
            "\t\t\"Description\": \"Tienes pagos vencidos de tu crédito. No pongas en riesgo tu vivienda, regularízate de inmediato.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"15\",\n" +
            "\t\t\"Description\": \"Continúa realizando tus pagos oportunamente.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"16\",\n" +
            "\t\t\"Description\": \"Falta un año para que termines de pagar tu crédito. Este último año el pago de tu crédito se cubre con las aportaciones patronales. Descarga e imprime tu aviso de suspensión; entrégaselo a tu patrón para que ya no te descuente el pago de tu crédito y asegúrate de que lo haga. Importante: en caso de que quedes desempleado debes pagar por tu cuenta la mensualidad. Consulta los establecimientos y la forma de hacerlo.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"17\",\n" +
            "\t\t\"Description\": \"Tu pago se refleja 10 días después de efectuado; consúltalo en la sección mis movimientos y estados de cuenta\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"18\",\n" +
            "\t\t\"Description\": \"* En cuanto al uso de tu prórroga, es importante que sepas que ésta no se activará de forma inmediata al no realizar el pago de tu mensualidad, Infonavit dará un tiempo de mantenimiento, esperando que puedas realizar algún pago, con la finalidad de evitar que el monto de tu saldo se incremente con el uso de la prórroga\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"19\",\n" +
            "\t\t\"Description\": \"ROA: Por nómina tu patrón te descuenta el pago de tu crédito. Pero los atrasos que presenta tu crédito, los debes cubrir por tu cuenta para que tu crédito se regularice.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"20\",\n" +
            "\t\t\"Description\": \"Crédito asignado a un despacho judicial\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"27\",\n" +
            "\t\t\"Description\": \"Si vas a liquidar tu crédito, paga directamente en cualquier banco autorizado.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"30\",\n" +
            "\t\t\"Description\": \"Tu crédito puede ser susceptible a un descuento por liquidación. Consulta tu saldo después del dia 7 del mes actual.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"31\",\n" +
            "\t\t\"Description\": \"Si vas a liquidar tu crédito con descuento, paga en una sola exhibición directamente en cualquier banco autorizado.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"33\",\n" +
            "\t\t\"Description\": \"Tu crédito está sujeto a una revisión del ingreso anual y si aumenta o disminuye con respecto al año anterior, se ajusta tu pago y la tasa de interés  conforme al promedio de tus percepciones.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"34\",\n" +
            "\t\t\"Description\": \"Desde que se originó, tu crédito cuenta con un complemento de pago, es decir, si pagas el importe pactado, el Infonavit te  da un beneficio para cubrir el importe total de la mensualidad con lo que asegura que tu crédito se termine de pagar en el tiempo programado.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"35\",\n" +
            "\t\t\"Description\": \"Tu crédito tiene un apoyo financiero temporal consistente en una Bonificación para el pago complementario de tu mensualidad. Dicha Bonificación irá disminuyendo con el tiempo.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"36\",\n" +
            "\t\t\"Description\": \"* REA: Por tu cuenta en los bancos, establecimientos comerciales autorizados o en línea, a través del centro de pagos del portal.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"37\",\n" +
            "\t\t\"Description\": \"* ROA: Por nómina  tu patrón te descuenta el pago de tu crédito.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"38\",\n" +
            "\t\t\"Description\": \"* EXT (por pérdida de empleo): Por tu cuenta en los bancos, establecimientos comerciales autorizados o en línea, a través del centro de pagos del portal.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"39\",\n" +
            "\t\t\"Description\": \"Por el incumplimiento de los pagos de tu crédito hipotecario se está iniciando un PROCESO JURIDICO y será asignado a un despacho de cobranza judicial.  Si buscas una solución acude a la gerencia de Cobranza de la delegación del Infonavit  más cercana.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"IdMessage\": \"40\",\n" +
            "\t\t\"Description\": \"Información actualizada al %s.\",\n" +
            "\t\t\"Fields\": [\n" +
            "\t\t\t\"v9FechaAsignacion\"\n" +
            "\t\t]\n" +
            "\t}\n" +
            "]";
}
