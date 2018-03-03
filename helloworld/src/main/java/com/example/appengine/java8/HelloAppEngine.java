/**
 * Copyright 2017 Google Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.java8;

// [START example]

import ai.api.model.Fulfillment;
import ai.api.model.GoogleAssistantResponseMessages;
import ai.api.model.ResponseMessage;
import ai.api.web.AIWebhookServlet;

import com.example.appengine.java8.repository.STATOPS;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;
import org.json.XML;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "HelloAppEngine", value = "/assistant")
public class HelloAppEngine extends AIWebhookServlet {

    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public static final String prontosoccorso = "prontosoccorso";
    public static final String showmenuaction = "showmenu";

    // URLs
    public static final String tgarURL = "http://ftp.tn.ymir.eu/tgar.jpg";
    public static final String povocompletoURL = "http://ftp.tn.ymir.eu/Povo02.jpg";
    public static final String mesiano2URL = "http://ftp.tn.ymir.eu/mesiano02.jpg";
    public static final String mesiano1URL = "http://ftp.tn.ymir.eu/mesiano01.jpg";

    public static final HashMap<String, String> webcamURLsList = new HashMap<String, String>() {{
        put("Tommaso Gar", tgarURL);
        put("Povo Completo", povocompletoURL);
        put("Mesiano 1", mesiano1URL);
        put("Mesiano 2", mesiano2URL);
    }};


    protected void doWebhook(AIWebhookRequest aiWebhookRequest, Fulfillment fulfillment) {
        List<ResponseMessage> messages_list = null;

        Gson gson = new Gson();

        if (aiWebhookRequest.getResult().getAction().equals(prontosoccorso)) {

            try {

                StringBuilder response = new StringBuilder();
                URL url = new URL("https://servizi.apss.tn.it/opendata/STATOPS001.xml");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject xmlJSONObj = XML.toJSONObject(response.toString());
                String jsonString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

                STATOPS statops = gson.fromJson(jsonString, STATOPS.class);

                statops.getDATAAGGIORNAMENTO();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        fulfillment.setMessages(messages_list);

    }

    private List<ResponseMessage> manageErrorMessage(AIWebhookRequest aiWebhookRequest) {

        List<ResponseMessage> messages_list = new ArrayList<>();

        //simple response
        GoogleAssistantResponseMessages.ResponseChatBubble simpleresponse = new GoogleAssistantResponseMessages.ResponseChatBubble();
        GoogleAssistantResponseMessages.ResponseChatBubble.Item chat_bubble_simple = new GoogleAssistantResponseMessages.ResponseChatBubble.Item();

        chat_bubble_simple.setTextToSpeech("Questa funzionalità non è disponibile su Google Home. Chiedimi qualcos'altro");

        List<GoogleAssistantResponseMessages.ResponseChatBubble.Item> itemList = new ArrayList<>();
        itemList.add(chat_bubble_simple);
        simpleresponse.setItems(itemList);
        messages_list.add(0, simpleresponse);

        return messages_list;
    }


    private List<ResponseMessage> manageWebcamAction(AIWebhookRequest aiWebhookRequest, Fulfillment fulfillment) {

        List<ResponseMessage> messages_list = new ArrayList<>();

        //simple response
        GoogleAssistantResponseMessages.ResponseChatBubble simpleresponse = new GoogleAssistantResponseMessages.ResponseChatBubble();
        GoogleAssistantResponseMessages.ResponseChatBubble.Item chat_bubble_simple = new GoogleAssistantResponseMessages.ResponseChatBubble.Item();

        if (aiWebhookRequest.getLang().startsWith("en-")) {
            chat_bubble_simple.setDisplayText("Here are the webcams! Do you want to ask me something else?");
            chat_bubble_simple.setTextToSpeech("Here are the webcams! Do you want to ask me something else?");

        } else {
            chat_bubble_simple.setDisplayText("Ecco le webcam! Posso aiutarti in qualche altro modo?");
            chat_bubble_simple.setTextToSpeech("Ecco le webcam! Posso aiutarti in qualche altro modo?");

        }
        List<GoogleAssistantResponseMessages.ResponseChatBubble.Item> itemList = new ArrayList<>();
        itemList.add(chat_bubble_simple);
        simpleresponse.setItems(itemList);


        // rich response
        GoogleAssistantResponseMessages.ResponseCarouselCard carouselCard = new GoogleAssistantResponseMessages.ResponseCarouselCard();
        List<GoogleAssistantResponseMessages.CardItem> cardItems = new ArrayList<>();

        for (String key : webcamURLsList.keySet()) {
            GoogleAssistantResponseMessages.CardItem webcamItem = new GoogleAssistantResponseMessages.CardItem();
            // set title
            webcamItem.setTitle(key);

            // set optionInfo
            GoogleAssistantResponseMessages.OptionInfo optionInfo = new GoogleAssistantResponseMessages.OptionInfo();
            optionInfo.setKey(webcamItem.getTitle());
            webcamItem.setOptionInfo(optionInfo);

            // set image
            GoogleAssistantResponseMessages.CardImage image = new GoogleAssistantResponseMessages.CardImage();
            image.setUrl(webcamURLsList.get(key));
            webcamItem.setImage(image);
            cardItems.add(webcamItem);
        }

        carouselCard.setItems(cardItems);
        messages_list.add(0, simpleresponse);
        messages_list.add(carouselCard);


        fulfillment.setSpeech(chat_bubble_simple.getTextToSpeech());

        return messages_list;
    }

    private List<ResponseMessage> manageMenuAction(AIWebhookRequest aiWebhookRequest, Fulfillment fulfillment) throws ParseException, IOException {

        List<ResponseMessage> messages_list = new ArrayList<>();

        GoogleAssistantResponseMessages.ResponseChatBubble simpleresponse = new GoogleAssistantResponseMessages.ResponseChatBubble();
        // setto il bubble message semplice
        GoogleAssistantResponseMessages.ResponseChatBubble.Item chat_bubble_simple = new GoogleAssistantResponseMessages.ResponseChatBubble.Item();
        List<GoogleAssistantResponseMessages.ResponseChatBubble.Item> itemList = new ArrayList<>();

        String simpletextresponse = null;

        JsonElement date = aiWebhookRequest.getResult().getParameters().get("date");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        sdt.setTimeZone(TimeZone.getTimeZone("UTC"));

        cal.setTime(sdt.parse(date.getAsString()));

        Gson gson = new Gson();
        StringBuilder response = new StringBuilder();
        URL url = null;
        if (aiWebhookRequest.getLang().startsWith("en-")) {
            url = new URL("https://unimeal-baa88.firebaseapp.com/menu2-english.txt");
        } else {
            url = new URL("https://unimeal-baa88.firebaseapp.com/menu2.txt");
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        Map<String, List<String>> parsed = gson.fromJson(response.toString(), Map.class);

        GoogleAssistantResponseMessages.ResponseListCard responseListCard = new GoogleAssistantResponseMessages.ResponseListCard();
        List<GoogleAssistantResponseMessages.CardItem> card_item_list = new ArrayList<>();
        String cal_as_string = String.valueOf(cal.getTimeInMillis());

        if (parsed.containsKey(cal_as_string)) {
            for (int i = 0; i < parsed.get(String.valueOf(cal.getTimeInMillis())).size(); ++i) {

                String actual_meal = parsed.get(String.valueOf(cal.getTimeInMillis())).get(i);

                GoogleAssistantResponseMessages.CardItem item = new GoogleAssistantResponseMessages.CardItem();
                GoogleAssistantResponseMessages.OptionInfo optionInfo = new GoogleAssistantResponseMessages.OptionInfo();
                optionInfo.setKey(actual_meal);

                item.setTitle(actual_meal);
                item.setOptionInfo(optionInfo);
                card_item_list.add(item);
            }

            responseListCard.setItems(card_item_list);
            messages_list.add(responseListCard);

            String complete_reply = null;
            String speech_menu = card_item_list.get(0).getTitle() + ", "
                    + card_item_list.get(1).getTitle() + ", "
                    + card_item_list.get(2).getTitle();
            String string_month = null;

            if (aiWebhookRequest.getLang().startsWith("en-")) {

                string_month = convertStringToDate(cal, Locale.ENGLISH);
                complete_reply = "Here's the menu for "
                        + cal.get(Calendar.DAY_OF_MONTH) + " "
                        + string_month + ". ";


                chat_bubble_simple.setDisplayText(complete_reply + ". " + speech_menu + ". Do you want to explore the menu for another day?");

                chat_bubble_simple.setTextToSpeech(complete_reply + ". " + speech_menu + ". Do you want to explore the menu for another day?");

            } else {

                string_month = convertStringToDate(cal, Locale.ITALIAN);
                complete_reply = "Ecco il menu per il "
                        + cal.get(Calendar.DAY_OF_MONTH) + " "
                        + string_month + " ";


                chat_bubble_simple.setDisplayText(complete_reply + ". " + speech_menu + ". Vuoi conoscere il menu per un altro giorno?");

                chat_bubble_simple.setTextToSpeech(complete_reply + ". " + speech_menu + ". Vuoi conoscere il menu per un altro giorno?");
            }

            itemList.add(chat_bubble_simple);

            // inserisco il bubble message all'interno della simpleresponse
            simpleresponse.setItems(itemList);
            messages_list.add(0, simpleresponse);

            // setto la risposta vocale di default
            fulfillment.setSpeech(parsed.get(String.valueOf(cal.getTimeInMillis())).stream().collect(Collectors.joining(", ")));

            fulfillment.setMessages(messages_list);

        } else {
            String final_response = null;
            if (aiWebhookRequest.getLang().startsWith("en-")) {
                simpletextresponse = "Let's see...";
                final_response = "I've found no menu for this day. Do you want to try with another day?";
            } else {
                simpletextresponse = "Vediamo...";
                final_response = "Non ho trovato nessun menu per questo giorno, vuoi provare per un altro?";

            }

            chat_bubble_simple.setDisplayText(final_response);

            chat_bubble_simple.setTextToSpeech(simpletextresponse + final_response);
            itemList.add(chat_bubble_simple);

            // inserisco il bubble message all'interno della simpleresponse
            simpleresponse.setItems(itemList);
            messages_list.add(0, simpleresponse);

            // setto la risposta vocale di default
            fulfillment.setSpeech(final_response);

            fulfillment.setMessages(messages_list);

        }
        return messages_list;
    }

    public String convertStringToDate(Calendar cal, Locale locale) {
        Date indate = new Date();
        indate.setTime(cal.getTimeInMillis());
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("MMMM", locale);
        /*you can also use DateFormat reference instead of SimpleDateFormat
         * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
         */
        try {
            dateString = sdfr.format(indate);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dateString;
    }
}
