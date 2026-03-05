package hub.guzio.MatrixTest;

import com.sun.net.httpserver.HttpServer;
import hub.guzio.MatrixTest.cannedHandlers.AliasEndpoint;
import hub.guzio.MatrixTest.cannedHandlers.MinecraftProtocol;
import hub.guzio.MatrixTest.cannedHandlers.UnknownEndpoint;
import hub.guzio.MatrixTest.cannedHandlers.UnknownProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    static void main() throws IOException {
        var sv = HttpServer.create(new InetSocketAddress(8080), 1000);

        //Core endpoints
        sv.createContext("/_matrix/app/v1/transactions/", new MainHandler());
        sv.createContext("/_matrix/app/v1/ping", new MainHandler());
        sv.createContext("/_matrix/app/v1/users/", new MainHandler());

        //Protocol endpoints
        sv.createContext("/_matrix/app/v1/thirdparty/protocol/minecraft", new MinecraftProtocol());
        sv.createContext("/_matrix/app/v1/thirdparty/user", new UserlistHandler());

        //Unknown endpoints
        sv.createContext("/_matrix/app/v1/rooms/", new AliasEndpoint());
        sv.createContext("/_matrix/app/v1/thirdparty/location", new AliasEndpoint());
        sv.createContext("/_matrix/app/v1/thirdparty/protocol/", new UnknownProtocol());
        sv.createContext("/", new UnknownEndpoint());

        sv.start();
        System.out.println("Started!");
    }
}