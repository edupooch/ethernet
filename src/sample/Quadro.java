package sample;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Classe que contém os métodos de criação de um quadro
 *
 * Created by edupooch
 */
public class Quadro {

    /**
     * ÍNDICES NO ARRAY DE BYTE ARRAY QUADRO, CADA UM DESSES INDICES CONTÉM UM ARRAY DE BYTES QUE REPRESENTAM UMA PARTE
     * DO QUADRO
     *
     */
    private static final int INDICE_PREAMBULO = 0;
    private static final int INDICE_SOF = 1;
    private static final int INDICE_FONTE = 2;
    private static final int INDICE_DESTINO = 3;
    private static final int INDICE_LENGHT = 4;
    private static final int INDICE_DADOS = 5; //PODE CONTER O PADDING TB
    private static final int INDICE_CRC = 6;

    /**
     * Tamanhos padronizados pela IEE 802.3
     */
    public static final int TAMANHO_MINIMO_DADOS = 46;
    private static final int TAMANHO_PREAMBULO = 8;
    public static final int TAMANHO_MAXIMO_DADOS = 1500;

    /**
     * Padrões de bits do preâmbulo e start of frame
     */
    private static final int PADRAO_PREAMBULO = (byte) 0b10101010;
    private static final byte PADRAO_SOF = (byte) 0b10101011;

    /**
     * Array de byte-array que representa o quadro
     */
    private static byte[][] quadro;


    public byte[][] criaQuadro(String enderecoDestino, String enderecoFonte, String dados) throws QuadroException {

        if (dados.length() > TAMANHO_MAXIMO_DADOS) {
            throw new QuadroException("Tamanho dos dados muito grade");
        } else {
            quadro = new byte[7][];
            definePreambulo();
            setEnderecoDestino(enderecoDestino);
            setEndrecoFonte(enderecoFonte);
            setTamanho(dados.length());
            setDados(dados);
            crc();
        }

        return quadro;
    }




    private void definePreambulo() {
        quadro[INDICE_PREAMBULO] = new byte[TAMANHO_PREAMBULO];
        for (int i = 0; i < TAMANHO_PREAMBULO; i++) {
            quadro[INDICE_PREAMBULO][i] = PADRAO_PREAMBULO;
        }
        defineSOF();
    }

    private void defineSOF() {
        quadro[INDICE_SOF] = new byte[1];
        quadro[INDICE_SOF][0] = PADRAO_SOF;
    }

    private void setEnderecoDestino(String enderecoDestino) {
        String[] stringsEnderecos = enderecoDestino.split(":");
        int tamanhoEnderecoDestino = stringsEnderecos.length;
        quadro[INDICE_DESTINO] = new byte[tamanhoEnderecoDestino];

        for (int i = 0; i < tamanhoEnderecoDestino; i++) {
            quadro[INDICE_DESTINO][i] = (byte) (Integer.parseInt(stringsEnderecos[i], 16) & 0xff);
        }

    }

    private void setEndrecoFonte(String enderecoFonte) {
        String[] stringsEnderecos = enderecoFonte.split(":");
        int tamanhoEnderecoFonte = stringsEnderecos.length;
        quadro[INDICE_FONTE] = new byte[tamanhoEnderecoFonte];
        for (int i = 0; i < tamanhoEnderecoFonte; i++) {
            quadro[INDICE_FONTE][i] =
                    (byte) (Integer.parseInt(stringsEnderecos[i], 16) & 0xff);
        }
    }

    private void setTamanho(int tamanho) {
        quadro[INDICE_LENGHT] = new byte[2];

        quadro[INDICE_LENGHT][0] = (byte) (tamanho & 0xFF);
        quadro[INDICE_LENGHT][1] = (byte) ((tamanho >> 8) & 0xFF);
    }

    private void setDados(String dados) {
        byte[] bytes = dados.getBytes();
        if (bytes.length < TAMANHO_MINIMO_DADOS) {
            padding(bytes);
        } else {
            quadro[INDICE_DADOS] = bytes;
        }
    }

    /**
     * Preenche o array de dados com 0's para atingir o tamanho minimo de 46 bytes previsto pelo padrão
     * @param bytesDados array de bytes com os dados que serão preenchidos
     */
    private void padding(byte[] bytesDados) {
        byte[] dadosComPadding = new byte[TAMANHO_MINIMO_DADOS];
        System.arraycopy(bytesDados, 0, dadosComPadding, 0,TAMANHO_MINIMO_DADOS - bytesDados.length);
        quadro[INDICE_DADOS] = dadosComPadding;
    }

    private void crc() {
        //classe que realiza algoritmo de crc
        CRC32 crc32 = new CRC32();
        crc32.update(quadro[INDICE_DESTINO]);
        crc32.update(quadro[INDICE_FONTE]);
        crc32.update(quadro[INDICE_LENGHT]);
        crc32.update(quadro[INDICE_DADOS]);

        ByteBuffer b = ByteBuffer.allocate(4);
        b.putLong(crc32.getValue());

        quadro[INDICE_CRC] = b.array();

    }
    
    /**
     * Tamanho dos dados pegando dois bytes do quadro que representam o tamanho e convertem pra int
     *
     * @return int com o tamanho dos dados
     */
    public int getTamanhoDados() {
        int high = quadro[INDICE_LENGHT][1] >= 0 ? quadro[INDICE_LENGHT][1] : 256 + quadro[INDICE_LENGHT][1];
        int low = quadro[INDICE_LENGHT][0] >= 0 ? quadro[INDICE_LENGHT][0] : 256 + quadro[INDICE_LENGHT][0];

        return low | (high << 8);
    }
}
