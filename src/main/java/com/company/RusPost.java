package com.company;

import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by sha on 04.09.2016.
 */
public class RusPost {
    String code = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;

    }

    public String connect()  {
        try {
            //Cоздаем соединение
            SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = soapConnFactory.createConnection();
            String url = "https://tracking.russianpost.ru/rtm34";

            //Cоздаем сообщение
            MessageFactory messageFactory = MessageFactory.newInstance("SOAP 1.2 Protocol");
            SOAPMessage message = messageFactory.createMessage();

            //Создаем объекты, представляющие различные компоненты сообщения
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();
            envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
            envelope.addNamespaceDeclaration("oper", "http://russianpost.org/operationhistory");
            envelope.addNamespaceDeclaration("data", "http://russianpost.org/operationhistory/data");
            envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
            SOAPElement operElement = body.addChildElement("getOperationHistory", "oper");
            SOAPElement dataElement = operElement.addChildElement("OperationHistoryRequest", "data");
            SOAPElement barcode = dataElement.addChildElement("Barcode", "data");
            SOAPElement messageType = dataElement.addChildElement("MessageType", "data");
            SOAPElement language = dataElement.addChildElement("Language", "data");
            SOAPElement dataAuth = operElement.addChildElement("AuthorizationHeader", "data");
            SOAPFactory sf = SOAPFactory.newInstance();
            Name must = sf.createName("mustUnderstand", "soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
            dataAuth.addAttribute(must, "1");
            SOAPElement login = dataAuth.addChildElement("login", "data");
            SOAPElement password = dataAuth.addChildElement("password", "data");

            //Заполняем значения
            barcode.addTextNode(code);
            messageType.addTextNode("0");
            language.addTextNode("RUS");
            login.addTextNode(Settings.LoginPost);
            password.addTextNode(Settings.PasswordPost);

            //Сохранение сообщения
            message.saveChanges();

            //Отправляем запрос и выводим ответ на экран
            SOAPMessage soapResponse = connection.call(message, url);
            Source sourceContent = soapResponse.getSOAPPart().getContent();
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(baos);
            t.transform(sourceContent, result);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

            //Закрываем соединение
            connection.close();
             return new XMLParser().parse(bais);
        }
        catch (Exception e){
            System.out.println("Байда в АПИ почты");
            return "Post Error";
        }
        }
        }
