package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ZipController {

    //Constantes
    static int TAMANHO_BUFFER = 4096; // 4kb

    //método para compactar arquivo
    public static void compactarParaZip() throws IOException {
        String[] arquivoEntrada = new String[100];

        System.out.println("Selecione os arquivos que deseje zipar.");
        
        //selecionando arquivos a serem zipados
        JFileChooser selecArq = new JFileChooser();
        selecArq.setDialogTitle("\nSelecione o(s) arquivo(s) desejado(s)               [Máx -> 100 arquivos]");
        selecArq.setMultiSelectionEnabled(true);

        if (selecArq.showDialog(null, "Abrir") == JFileChooser.APPROVE_OPTION) {
            System.out.println("\nArquivos selecionados:\n" + Arrays.toString(selecArq.getSelectedFiles()).replace("\\", "\\\\").trim());
            arquivoEntrada = Arrays.toString(selecArq.getSelectedFiles()).replace("[", "").replace("]", "").trim().split(",");
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado ou arquivo inválido.", "Falha ao abrir arquivo", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        System.out.println("\nSelecione o diretório que deseja salvar o arquivo zipado.");
        
        //escolhendo diretório a salvar o .zip
        JFileChooser selecArqDestino = new JFileChooser();

        String caminhoArquivoDestino = "";
        if (selecArqDestino.showDialog(null, "Salvar") == JFileChooser.APPROVE_OPTION) {
            caminhoArquivoDestino = (selecArqDestino.getSelectedFile().getPath()).trim();
            caminhoArquivoDestino = caminhoArquivoDestino.replaceAll(".zip", "");
            caminhoArquivoDestino = caminhoArquivoDestino.concat(".zip");
        } else {
            JOptionPane.showMessageDialog(null, "Os dados do centróide não foram salvos.", "Falha ao salvar arquivo", JOptionPane.ERROR_MESSAGE);
            System.out.println("O arquivo não foi salvo, use a Opção '4' para visualizar a(s) nova(s) coordenada(s) do(s) centróide(s).");
            System.exit(0);
        }
        
        int cont;
        byte[] dados = new byte[TAMANHO_BUFFER];

        System.out.println("\nOs arquivos estão sendo processados...");
        
        //zipando todos os arquivos em um único .zip
        FileOutputStream destino = new FileOutputStream(new File(caminhoArquivoDestino.trim()));
        try (ZipOutputStream saida = new ZipOutputStream(new BufferedOutputStream(destino))) {
            for (String arqEntrada : arquivoEntrada) {
                
                String caminhoTemp = arqEntrada.replace("\\", "\\\\").trim();
                File file = new File(caminhoTemp);
                FileInputStream streamDeEntrada = new FileInputStream(file);
                
                System.out.println("\nO arquivo: '".concat(caminhoTemp.replace("\\\\", "\\")).concat("' está sendo zipado neste momento."));
                
                try (BufferedInputStream origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    saida.putNextEntry(entry);

                    while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
                        saida.write(dados, 0, cont);
                    }
                }catch(Exception e){
                    System.out.println("\n\nErro ao ler arquivos.\n\n".concat(e.getMessage().trim()));
                    System.exit(0);
                }
            }
            System.out.println("\nFIM");
        }catch(Exception e){
            System.out.println("\n\nErro ao gerar arquivo .zip\n\n".concat(e.getMessage().trim()));
            System.exit(0);
        }
    }
}
