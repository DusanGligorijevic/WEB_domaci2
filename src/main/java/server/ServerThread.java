package server;

import com.google.gson.Gson;
import model.*;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    private static int flag=0;
    private Gson gson;
    private Table table;
    Thread game;

    public ServerThread(Socket socket, Table table) {
        this.socket = socket;
        this.table = table;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gson = new Gson();
    }

    public void run() {

        try {
            Request request = receiveRequest();

            Player player = new Player(request.getId());

            Response response = new Response();
            response.setResult(Result.FAILURE);

            if(request.getAction() == Action.REQUEST_CHAIR) {
                if(table.giveSeat(player)){
                    response.setResult(Result.SUCCESS);
                    System.out.println("Igrač "+player.getId()+" je seo na mesto broj "+ player.getNumberOfSeat());
                    flag++;
                }else
                    System.out.println("Igrač "+player.getId()+ " je izašao iz prostorije.");
                sendResponse(response);
            }

            // TODO
           if(flag==6){
               flag++;
               game= new Thread (new Game(table));
               game.start();
           }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Request receiveRequest() throws IOException {
        return gson.fromJson(in.readLine(), Request.class);
    }

    private void sendResponse(Response response) {
        out.println(gson.toJson(response));
    }
}
