import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://alura-filmes.herokuapp.com/conteudos";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // pegar só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("Título: " + "\u001b[1m" + filme.get("title") + "\u001b[0m");
            System.out.println("Poster: " + "\u001b[1m" + filme.get("image") + "\u001b[0m");
            Double nota = Double.parseDouble(filme.get("imDbRating"));
            var arrendondamento = Math.ceil(nota);
            System.out.println("\u001b[37m\u001b[48;5;126mClassificação: " + arrendondamento + "\u001b[0m");
            for (int i = 0; i < arrendondamento; i++) {
                System.out.print("\u2B50");
            }
            System.out.println("\n");
        }
    }
}
