import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;;

class SearchEngine implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    ArrayList<String> wordList = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {

            String message = """

                    Here are some of the possible commands you could run:

                    /add?s=anewstringtoadd

                    /add?s=pineapple

                    /add?s=apple

                    /search?s=app
                    (would return pineapple and apple)""";

            return String.format(message);
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String stringToAdd = parameters[1];
                wordList.add(stringToAdd);
                return "The string " + stringToAdd + "was successfully added to the list" + "\n\n" + wordList;
            }
            return "404 Not Found!";

        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("search")) {
                String sub = parameters[1];
                ArrayList<String> stringsWithSub = new ArrayList<String>();

                for (int i = 0; i < wordList.size(); i++) {
                    if (wordList.get(i).contains(sub)) {
                        stringsWithSub.add(wordList.get(i));
                    }
                }
                String message = "The following strings contain the word" + sub + ":" + "\n\n" + stringsWithSub;
                return message;
            }
            return "404 Not Found!";

        } else {
            return "404 Not Found!";
        }
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
