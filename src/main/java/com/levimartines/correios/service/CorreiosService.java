package com.levimartines.correios.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Service
public class CorreiosService {

    public Double getValorFrete(String cepDestino) {
        String cepFormatado = cepDestino.replaceAll("-", "");
        String valorFreteCorreios = getValorFreteFromCorreios(cepFormatado);
        return Double.parseDouble(valorFreteCorreios) + 4.0;
    }

    private String getValorFreteFromCorreios(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?" +
                "sCepOrigem=18051020&" +
                "nVlPeso=1&nCdFormato=1&" +
                "nVlComprimento=16&" +
                "nVlAltura=5&nVlLargura=11&" +
                "sCdMaoPropria=n&" +
                "nVlValorDeclarado=0&" +
                "sCdAvisoRecebimento=n&" +
                "nCdServico=04510&" +
                "nVlDiametro=0&" +
                "StrRetorno=xml&" +
                "sCepDestino=" + cep;
        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl, String.class);
        String xmlString = result.getBody();
        int initValor = xmlString.indexOf("<Valor>") + "<Valor>".length();
        int fimValor = xmlString.indexOf("</Valor>");
        String valorFrete = xmlString.substring(initValor, fimValor);
        return valorFrete.replaceAll(",", ".");
    }

}
