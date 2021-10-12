package server;

import com.google.gson.Gson;
import dto.GuessTheNumberMessage;
import org.xml.sax.SAXException;
import util.WriteReadHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static File stateFile;

    public static void main(String[] args) {
        try {
            try {
                Gson parser = new Gson();
                server = new ServerSocket(4004);
                stateFile = new File("state.xml");
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = schemaFactory.newSchema(new File("src/main/resources/schema1.xsd"));
                JAXBContext jaxbContext = JAXBContext.newInstance(GuessTheNumberMessage.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                marshaller.setSchema(schema);
                unmarshaller.setSchema(schema);
                Socket clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String message;
                    GuessTheNumberMessage mes = initializeFistMessageAccordingToStateFileExisting(unmarshaller);
                    int number = mes.number;
                    marshaller.marshal(mes, stateFile);
                    WriteReadHandler.write(out, parser.toJson(mes));
                    while (true) {
                        message = WriteReadHandler.read(in);
                        mes = parser.fromJson(message, GuessTheNumberMessage.class);
                        if (number == mes.number) {
                            mes.startEndGameFlag = false;
                            mes.message = "Игра завершена, число угадано";
                            WriteReadHandler.write(out, parser.toJson(mes));
                            stateFile.delete();
                            break;
                        } else {
                            if (mes.number > number) {
                                mes.message = "Введенное число больше чем загаданное";
                            } else {
                                mes.message = "Введенное число меньше чем загаданное";
                            }
                            WriteReadHandler.write(out, parser.toJson(mes));
                        }
                    }
                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                server.close();
            }
        } catch (IOException | JAXBException | SAXException e) {
            System.err.println(e);
        }
    }

    private static GuessTheNumberMessage initializeFistMessageAccordingToStateFileExisting(Unmarshaller unmarshaller)
            throws JAXBException, SAXException {
        GuessTheNumberMessage message;
        if (!stateFile.exists()) {
            System.out.println("Загадайте число:");
            message = new GuessTheNumberMessage("Игра началась", WriteReadHandler.readIntFromConsole(), true);
        } else {
            System.out.println("Прошлая игра была завершена некорректно");
            System.out.println("Прошлое число:");
            GuessTheNumberMessage savedMessage = (GuessTheNumberMessage) unmarshaller.unmarshal(stateFile);
            System.out.println("Прошлое число:" + savedMessage.number);
            message = new GuessTheNumberMessage("Игра начала из сохраненного состояния", savedMessage.number, true);
        }
        return message;
    }
}
