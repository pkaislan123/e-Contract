package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import cadastros.CadastroModelo;
import conexaoBanco.GerenciarBancoContratos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JTextField;
import javax.swing.JTextArea;



public class TelaImportarModelo extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entArquivoSelecionado;
	private JTextField entNomeArquivo;
	private JLabel lblDescrio;


	public TelaImportarModelo() {
		setAlwaysOnTop(true);

		//setModal(true);

		TelaImportarModelo isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Importar Modelo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblArquivo = new JLabel("Arquivo:");
		lblArquivo.setFont(new Font("Arial", Font.BOLD, 20));
		lblArquivo.setBounds(50, 77, 89, 32);
		painelPrincipal.add(lblArquivo);
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//selecionar arquivo
				JFileChooser arquivo = new JFileChooser();
				FileNameExtensionFilter filtroexcel = new FileNameExtensionFilter("Excel", "xlsx");  
				FileNameExtensionFilter filtroword = new FileNameExtensionFilter("Word", "docx");  

				arquivo.addChoosableFileFilter(filtroexcel);
				arquivo.addChoosableFileFilter(filtroword);

				arquivo.setAcceptAllFileFilterUsed(false);
				if(arquivo.showOpenDialog(isto) == JFileChooser.APPROVE_OPTION){
					entArquivoSelecionado.setText(arquivo.getSelectedFile().getAbsolutePath());
				}
			}
			
		});
		btnSelecionar.setBounds(444, 77, 89, 32);
		painelPrincipal.add(btnSelecionar);
		
		entArquivoSelecionado = new JTextField();
		entArquivoSelecionado.setBounds(148, 77, 275, 32);
		painelPrincipal.add(entArquivoSelecionado);
		entArquivoSelecionado.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 20));
		lblNome.setBounds(69, 168, 71, 32);
		painelPrincipal.add(lblNome);
		
		entNomeArquivo = new JTextField();
		entNomeArquivo.setColumns(10);
		entNomeArquivo.setBounds(148, 171, 275, 32);
		painelPrincipal.add(entNomeArquivo);
		
		lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Arial", Font.BOLD, 20));
		lblDescrio.setBounds(28, 211, 111, 32);
		painelPrincipal.add(lblDescrio);
		
		JTextArea entDescricao = new JTextArea();
		entDescricao.setBounds(148, 214, 275, 72);
		entDescricao.setBackground(SystemColor.inactiveCaption);
		painelPrincipal.add(entDescricao);
		
		JButton btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = entArquivoSelecionado.getText();
				String nome_modelo = entNomeArquivo.getText();
				String descricao_modelo = entDescricao.getText();
				
				if(path == null || path.equals("") || path.equals(" "))
				{
		              JOptionPane.showMessageDialog(null, "Caminho do Arquivo Invalido");
					
				}
				else
				{
					if(nome_modelo == null || nome_modelo.equals("") || nome_modelo.equals(" "))
					{
			              JOptionPane.showMessageDialog(null, "Nome não pode ser vazio");

					}
					else
					{
						if(descricao_modelo == null || descricao_modelo.equals("") || descricao_modelo.equals(" "))
						{
				              JOptionPane.showMessageDialog(null, "Descrição não pode ser vazio");

						}
						else
						{
							File f = null;
							//converte arquivo para array de bytes
							try {
							 f = new File(path);
								System.out.println("Arquivo criado");

							}catch(Exception k)
							{
								System.out.println("Erro ao criar o arquivo apartir do endereço");
							}
							
							InputStream is;
							try {
								is = new FileInputStream(f);
								 byte[] bytes = new byte[(int)f.length() ];
							        int offset = 0;
							        int numRead = 0;
							        while (offset < bytes.length
							               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
							            offset += numRead;
							        }
							        
							        CadastroModelo modelo = new CadastroModelo();
							        modelo.setNome_modelo(nome_modelo);
							        modelo.setDescricao_modelo(descricao_modelo);
							        modelo.setArquivo(bytes);
							        
							        GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
							        if(gerenciar.inserir_modelo(modelo) == 1) {
							              JOptionPane.showMessageDialog(null, "Sucesso ao cadastrar");
							              isto.dispose();

							        }
							        else
							        {
							              JOptionPane.showMessageDialog(null, "Erro ao cadastrar");

							        }
							        
							        		
							        
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						       
							
						}
					}
				}
			}
		});
		btnImportar.setBounds(532, 386, 89, 23);
		painelPrincipal.add(btnImportar);
		
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}
}
