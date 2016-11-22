package modelo;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/**
 * Classe que contém os métodos de criação de um quadro
 * <p>
 * Created by edupooch
 */
abstract class Quadro {

    /**
     * ÍNDICES NO ARRAY DE BYTE ARRAY QUADRO, CADA UM DESSES INDICES CONTÉM UM ARRAY DE BYTES QUE REPRESENTAM UMA PARTE
     * DO QUADRO
     */
    private static final int INDICE_PREAMBULO = 0;
    private static final int INDICE_SOF = 1;
    private static final int INDICE_FONTE = 2;
    private static final int INDICE_DESTINO = 3;
    private static final int INDICE_LENGHT = 4;
    private static final int INDICE_DADOS = 5; //PODE CONTER O PADDING
    private static final int INDICE_CRC = 6;

    /**
     * Tamanhos padronizados pela IEE 802.3
     */
    private static final int TAMANHO_MINIMO_DADOS = 46;
    private static final int TAMANHO_PREAMBULO = 7;
    private static final int TAMANHO_MAXIMO_DADOS = 1500;

    /**
     * Padrões de bits do preâmbulo e start of frame
     */
    private static final int PADRAO_PREAMBULO = (byte) 0b10101010;
    private static final byte PADRAO_SOF = (byte) 0b10101011;

    /**
     * Array de byte-array que representa o quadro
     */
    private static byte[][] quadro;


    public static byte[][] criaQuadro(String enderecoDestino, String enderecoFonte, String dados) {

        if (dados.length() > TAMANHO_MAXIMO_DADOS) {
            try {
                throw new QuadroException("Tamanho dos dados muito grande");
            } catch (QuadroException e) {
                e.printStackTrace();
            }
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


    private static void definePreambulo() {
        quadro[INDICE_PREAMBULO] = new byte[TAMANHO_PREAMBULO];
        for (int i = 0; i < TAMANHO_PREAMBULO; i++) {
            quadro[INDICE_PREAMBULO][i] = PADRAO_PREAMBULO;
        }
        defineSOF();
    }

    private static void defineSOF() {
        quadro[INDICE_SOF] = new byte[1];
        quadro[INDICE_SOF][0] = PADRAO_SOF;
    }

    private static void setEnderecoDestino(String enderecoDestino) {
        String[] stringsEnderecos = enderecoDestino.split(":");
        int tamanhoEnderecoDestino = stringsEnderecos.length;
        quadro[INDICE_DESTINO] = new byte[tamanhoEnderecoDestino];

        for (int i = 0; i < tamanhoEnderecoDestino; i++) {
            quadro[INDICE_DESTINO][i] = (byte) (Integer.parseInt(stringsEnderecos[i], 16) & 0xff);


        }

    }

    private static void setEndrecoFonte(String enderecoFonte) {
        String[] stringsEnderecos = enderecoFonte.split(":");
        int tamanhoEnderecoFonte = stringsEnderecos.length;
        quadro[INDICE_FONTE] = new byte[tamanhoEnderecoFonte];
        for (int i = 0; i < tamanhoEnderecoFonte; i++) {
            quadro[INDICE_FONTE][i] =
                    (byte) (Integer.parseInt(stringsEnderecos[i], 16) & 0xff);
        }
    }

    private static void setTamanho(int tamanho) {
        quadro[INDICE_LENGHT] = new byte[2];

        quadro[INDICE_LENGHT][0] = (byte) (tamanho & 0xFF);
        quadro[INDICE_LENGHT][1] = (byte) ((tamanho >> 8) & 0xFF);
    }

    private static void setDados(String dados) {
        byte[] bytes = dados.getBytes();
        if (bytes.length < TAMANHO_MINIMO_DADOS) {
            padding(bytes);
        } else {
            quadro[INDICE_DADOS] = bytes;
        }
    }

    /**
     * Preenche o array de dados com 0's para atingir o tamanho minimo de 46 bytes previsto pelo padrão
     *
     * @param bytesDados array de bytes com os dados que serão preenchidos
     */
    private static void padding(byte[] bytesDados) {
        byte[] dadosComPadding = new byte[TAMANHO_MINIMO_DADOS];
        System.arraycopy(bytesDados, 0, dadosComPadding, 0, TAMANHO_MINIMO_DADOS - (TAMANHO_MINIMO_DADOS - bytesDados.length));
        quadro[INDICE_DADOS] = dadosComPadding;
    }

    private static void crc() {
        //classe que realiza algoritmo de crc
        int valorCRC = criaValorCRC(quadro);

        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(valorCRC);


        quadro[INDICE_CRC] = b.array();

    }



    /**
     * Tamanho dos dados pegando dois bytes do quadro que representam o tamanho e convertem pra int
     *
     * @return int com o tamanho dos dados
     */
    private static int getTamanhoDados(byte[][] quadro) {
        int high = quadro[INDICE_LENGHT][1] >= 0 ? quadro[INDICE_LENGHT][1] : 256 + quadro[INDICE_LENGHT][1];
        int low = quadro[INDICE_LENGHT][0] >= 0 ? quadro[INDICE_LENGHT][0] : 256 + quadro[INDICE_LENGHT][0];

        return low | (high << 8);
    }

    public static int getTamanhoTotal(byte[][] quadro) {
        int tamanho = 0;
        for (byte[] array : quadro) tamanho += array.length;
        return tamanho;
    }

    public static String getDescricao(byte[][] quadro) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n------------------------------------------");
        stringBuilder.append("\nTamanho total: ");
        stringBuilder.append(getTamanhoTotal(quadro)).append(" bytes");

        stringBuilder.append("\nPrelúdio: ");
        for (byte pre :
                quadro[INDICE_PREAMBULO]) {
            stringBuilder.append(Integer.toString(pre + 128, 2));
        }

        stringBuilder.append("\nStart of Frame: ").append(Integer.toString(quadro[INDICE_SOF][0] + 128, 2));

        stringBuilder.append("\nEndereço de Destino: ");
        stringBuilder.append(getEnderecoDestino(quadro));

        stringBuilder.append("\nEndereço da Fonte: ");
        String fonte = DatatypeConverter.printHexBinary(quadro[INDICE_FONTE]).replaceAll("(.{2})", "$1:");
        stringBuilder.append(fonte.substring(0, fonte.length() - 1));

        stringBuilder.append("\nTamanho dos Dados: ");
        stringBuilder.append(getTamanhoDados(quadro)).append(" bytes");

        stringBuilder.append("\nDados: ");
        stringBuilder.append(new String(quadro[INDICE_DADOS]));

        stringBuilder.append("\nCRC-32: ");
        stringBuilder.append(leValorCRC(quadro));

        stringBuilder.append("\n------------------------------------------\n");


        return stringBuilder.toString();
    }

    public static int leValorCRC(byte[][] quadro) {
        ByteBuffer buffer = ByteBuffer.wrap(quadro[INDICE_CRC]);
        return buffer.getInt();
    }

    static String getEnderecoDestino(byte[][] infoRecebida) {
        String destino = DatatypeConverter.printHexBinary(infoRecebida[INDICE_DESTINO]).replaceAll("(.{2})", "$1:");
        return destino.substring(0, destino.length() - 1);
    }

    public static int criaValorCRC(byte[][] quadro) {
        CRC32 crc32 = new CRC32();
        crc32.update(quadro[INDICE_DESTINO]);
        crc32.update(quadro[INDICE_FONTE]);
        crc32.update(quadro[INDICE_LENGHT]);
        crc32.update(quadro[INDICE_DADOS]);

        return (int) crc32.getValue();
    }

}
