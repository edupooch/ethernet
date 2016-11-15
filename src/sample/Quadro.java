package sample;

import java.util.Arrays;

/**
 * Created by nilton-pc on 14/11/2016.
 */
public class Quadro {

    //Tamanhos e indices
    private static final int TAMANHO_PREAMBULO = 8;
    private static final int INDICE_SOF = 7;
    private static final int TAMANHO_TAMANHO = 2;
    private static final int INDICE_PREAMBULO = 0;
    public static final int PADRAO_PREAMBULO = (byte) 0b10101010;
    public static final byte PADRAO_SOF = (byte) 0b10101011;
    private static final int INDICE_LENGHT = 4;
    private static final int INDICE_FONTE = 2;
    private static final int INDICE_DESTINO = 3;
    private int tamanhoEnderecoDestino;
    private int ind_dados;

    private byte[][] quadro;
    

    private int tamanhoEnderecoFonte;



    public byte[][] criaQuadro(String enderecoDestino, String enderecoFonte, String dados) {
        if (dados.length() == 0){
            quadro = new byte[7][];
        } else if (dados.length() == 1500){
            quadro = new byte[7][]; //sem padding
        } else if (dados.length() > 1500){
            System.out.println("Esse dado é muito grande para ser transmitido");
        } else { // lenght entre 1 e 1500

            definePreambulo();
            setEnderecoDestino(enderecoDestino);
            setEndrecoFonte(enderecoFonte);
            setTamanho(dados);
            setDados(dados);
        }

        return quadro;
    }



    private void definePreambulo() {
        quadro[INDICE_PREAMBULO] = new byte[TAMANHO_PREAMBULO];
        for (int i=0; i < TAMANHO_PREAMBULO;i++){
            quadro[INDICE_PREAMBULO][i] =  PADRAO_PREAMBULO;;
        }
        defineSOF();
    }
    private void defineSOF() {
        quadro[INDICE_SOF] = new byte[1];
        quadro[INDICE_SOF][0] = PADRAO_SOF;
    }

    private void setEnderecoDestino(String enderecoDestino) {
        String[] stringsEnderecos = enderecoDestino.split(":");
        tamanhoEnderecoDestino = stringsEnderecos.length;
        quadro[INDICE_DESTINO] = new byte[tamanhoEnderecoDestino];

        for (int i = 0; i < tamanhoEnderecoDestino; i++) {
            quadro[INDICE_DESTINO][i] = (byte) (Integer.parseInt(stringsEnderecos[i], 16) & 0xff);
        }

    }

    private void setEndrecoFonte(String enderecoFonte) {
        String[] stringsEnderecos = enderecoFonte.split(":");
        tamanhoEnderecoFonte = stringsEnderecos.length;
        quadro[INDICE_FONTE] = new byte[tamanhoEnderecoFonte];
        for (int i = 0; i < tamanhoEnderecoFonte; i++) {
            quadro[INDICE_FONTE][i] =
                    (byte) (Integer.parseInt(stringsEnderecos[i], 16) & 0xff);
        }
    }

    private void setDados(String dados) {
        //quadro[ind_dados] = dados;
    }

    private void setTamanho(String dados) {
        int tamanho = dados.length();
        quadro[INDICE_LENGHT][0] = (byte) (tamanho & 0xFF);
        quadro[INDICE_LENGHT][1] = (byte) ((tamanho >> 8) & 0xFF);
    }

    /**
     * Tamanho dos dados pegando dois bytes do quadro que representam o tamanho e convertem pra int
     *
     * @return int com o tamanho dos dados
     */
    public int getTamanhoDados(){
        int high = quadro[INDICE_LENGHT][1]  >= 0 ? quadro[INDICE_LENGHT][1] : 256 + quadro[INDICE_LENGHT][1];
        int low = quadro[INDICE_LENGHT][0] >= 0 ? quadro[INDICE_LENGHT][0] : 256 + quadro[INDICE_LENGHT][0];

        return low | (high << 8);
    }
}
