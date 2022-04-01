package br.com.jdorigao.apispringsecurity;

import br.com.jdorigao.apispringsecurity.entity.*;
import br.com.jdorigao.apispringsecurity.util.Util;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

public class Teste {

    private static final String URL_BASE = "http://localhost:9090/api/v1/";
    private static final String URL_VENDA = URL_BASE + "venda";
    private static final String TOKEN = "@pi123Yhdfg5ai6#";

    public static void main(String[] args) {

        try {
            //enviaVenda();
            buscarVendas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enviaVenda() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost(URL_VENDA);
        post.setHeader("Content-type", "application/json");

        Venda venda = criaVenda();
        String jsonEnvio = Util.objectToJson(venda);
        post.setEntity(new StringEntity(jsonEnvio));

        CloseableHttpResponse response = httpClient.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200 && statusCode != 201) {
            throw new HttpException("Erro ao enviar requisição " + statusCode);
        }

        String jsonResposta = EntityUtils.toString(response.getEntity());
        System.out.println(jsonResposta);
    }

    private static void buscarVendas() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet get = new HttpGet(URL_VENDA+"/99999999999999");
        get.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = httpClient.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200 && statusCode != 201) {
            throw new HttpException("Erro ao enviar requisição " + statusCode);
        }

        String jsonResposta = EntityUtils.toString(response.getEntity());
        System.out.println(jsonResposta);
    }

    private static Venda criaVenda() {
        Venda venda = new Venda();
        venda.setDataVenda(LocalDateTime.now());
        venda.setValorTotal(BigDecimal.TEN);
        venda.setCnpj("99999999999999");

        Caixa caixa = new Caixa();
        caixa.setValorFechamento(BigDecimal.TEN);
        caixa.setValorAbertura(BigDecimal.TEN);
        caixa.setDataFechamento(LocalDate.now());
        caixa.setDataFechamento(LocalDate.now());
        caixa.setEstado(true);
        venda.setCaixa(caixa);

        VendaPagamento pagamento = new VendaPagamento();
        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setNome("Dinheiro");
        pagamento.setPagamento(tipoPagamento);
        pagamento.setValor(BigDecimal.TEN);
        venda.setPagamentos(Collections.singletonList(pagamento));

        VendaProduto vendaProduto = new VendaProduto();
        vendaProduto.setQuantidade(BigDecimal.ONE);
        vendaProduto.setTotal(BigDecimal.TEN);

        Produto produto = new Produto();
        produto.setNome("Produto");
        produto.setValor(BigDecimal.TEN);
        vendaProduto.setProduto(produto);
        venda.setProdutos(Collections.singletonList(vendaProduto));

        return venda;
    }
}
