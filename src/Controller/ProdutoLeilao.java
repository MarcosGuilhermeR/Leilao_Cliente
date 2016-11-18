/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author stephany
 */
public class ProdutoLeilao implements Serializable {
    
    protected long codigoProduto; // time em millisegundos no momento do cadastro do produto - unicidade
    protected String nomeProduto;
    protected double precoInicial;
    protected int tempoLeilao;
    protected double maiorValor = 0;
    protected String nomeVendedor;
    protected long tempoInicialLeilao = System.currentTimeMillis();  //inicializa o contador do leilão no momento do cadastro do novo produto
    protected String nomeVencedor = null;
    protected ArrayList assinantes = new ArrayList(); // pessoas (possiveis compradores) interessados no leilão
    protected long fimTempoLeilao;
    
    
    public long getFimTempoLeilao()
    {
        return this.fimTempoLeilao;
    }
    
    public void setFimTempoLeilao(long fimTempoLeilao)
    {
        this.fimTempoLeilao = fimTempoLeilao;
    }
    
    public ArrayList getAssinantes()
    {
        return this.assinantes;
    }
    
    public void setAssinantes(ArrayList assinantes)
    {
        this.assinantes = assinantes;
    }
    
    public String getNomeVencedor()
    {
        return this.nomeVencedor;
    }
    
    public void setNomeVencedor(String nomeVencedor)
    {
        this.nomeVencedor = nomeVencedor;
    }
    
    
    public void setMaiorValor(double maiorValor)
    {
        this.maiorValor = maiorValor;
    }
    
    public long getCodigoProduto()
    {
        return this.codigoProduto;
    }
    
    public String getNomeProduto()
    {
        return this.nomeProduto;
    }
    
    public double getPrecoInicial()
    {
        return this.precoInicial;
    }
    
    public int getTempoLeilao()
    {
        return this.tempoLeilao;
    }
    
    public double getMaiorValor()
    {
        if(this.maiorValor == 0)        
        {
            return this.precoInicial;
        }else
            return this.maiorValor;
    }
    
    public long getTempoInicialLeilao()
    {
        return this.tempoInicialLeilao;
    }
   
    public String getNomeVendedor()
    {
        return this.nomeVendedor;
    }
    
    public ProdutoLeilao(long codigoProduto, String nomeProduto, double precoInicial, int tempoLeilao, String nomeVendedor)
    {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.precoInicial = precoInicial;
        this.tempoLeilao = tempoLeilao;
        this.nomeVendedor = nomeVendedor;
        
    }
    
}
