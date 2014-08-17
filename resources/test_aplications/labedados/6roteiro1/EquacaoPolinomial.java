import java.util.*;
import java.util.regex.Pattern;
//import java.io.*;
/**
 *Uma classe que implementa a inteface Polinomio, para gerar um objeto EquacaoPolinomial, 
 *com dois campos, um nome para a equa��o polinomial, e uma equa��o que � a ex�ncia do 
 *projeto, nessa classe se encontra os comandos para se manipular uma equa��o polinomial
 *
 *@author H�lio Cardoso R. de Souza & Ronaldo 
 *@version 1.0 
 */
public class EquacaoPolinomial implements Polinomio{
	
	private String nome;
	private String equacao;
	//contrutor sem argumentos para a classe Equa��o Polinomial
	public  EquacaoPolinomial(){
		this.nome = "";
		this.equacao = "";
	}
	
	/**
     * Constroi uma <code>EquacaoPolinomial </code> com o nome e a equa��o determinada
     * e sua equa��o, note que esse construtor j� formata a equa��o, ou seja, a equa��o
     * j� esta na forma como vai ser impressa. 
     *
     * @param  nome o nome da equa��o 
     * @param equacao a equa��o polinomial definida
     * @return void
     */
	public EquacaoPolinomial(String nome, String equacao){
		
		this.nome=nome;
		boolean teste = true;
		String resultado = "";
		Scanner scr = new Scanner(equacao.trim()).useDelimiter(Pattern.compile("[+-]+"));
		try{
			for(int k = 0 ; k < equacao.trim().length(); k++){
				if(equacao.trim().charAt(k) == '-' && k == 0){
					resultado += "-" + scr.next().trim();
				}
				if(equacao.trim().charAt(k) == '-' && k != 0){
					resultado = resultado+" "  + "-" +" "+ scr.next().trim();
				}
				if (equacao.trim().charAt(k) == '+'){
					resultado = resultado +" " +  "+" + " " +scr.next().trim() ;
				}
				if(equacao.trim().charAt(k) != '-' && equacao.trim().charAt(k) != '+' && teste == true && k == 0){
					resultado = resultado + scr.next().trim();
					teste = false;
				}
			}
		}catch(Exception e){}
		this.equacao = resultado;
    }
	public EquacaoPolinomial(String str){
		equacao = str;
	}
	/**
	* Sets o nome da eqa��o polinomial
	* @param nome o mone da equa��o
	*throws 
	*/
	
	public void setNome(String nome){
		this.nome = nome;
	}
	/**
	* Sets a eqa��o polinomial
	* @param equa��o a fun��o da equa��o polinomial
	*throws 
	*/
	public void setEquacao(String equacao){
		this.equacao = equacao;
	}
	/**
	* Gets o nome da eqa��o polinomial
	* @param nome o mone da equa��o
	*throws 
	*/
	public String getNome(){
		return nome;
	}
	public String getEquacao(){
		return equacao;
	}
	/**
     * Calcula o resultado da equa��o polinomial dado um numero inteiro qualquer
     *
     * @param  x um inteiro a ser calculado
     * @return o resultado da equa��o em fun��o do inteiro
     */
	public int calculaEquacao(int x){
		double result = 0.0;
		try{
		
			Scanner scr = new Scanner(this.equacao);
			result = parcial(scr.next(), x);
		
			while(scr.hasNext()){
				String parte = scr.next();
				if(parte.equals("+")){
					result = result + parcial(scr.next(), x);
				}
				else if(parte.equals("-") || parte.equals("")){
					result = result - parcial(scr.next(), x );
				}
			}
		
		}catch(Exception e)	{}
		return (int)result;	
		
	}
	/**
     * Calcula o resultado de um monomio de uma equa��o polinomial
     * 
     *
     * @param pacial um String representando o monomio de uma equa��o polinomial
     * @param x um inteiro comtendo o valor que se deseja calcular
     * @return o resultado da substitui��o do valor pela icognita do mon�nio
     */
	public double parcial(String monomio , int x){
		double resultado = 0.0;
		
		try{
			int numero = 1;
			int expoente = 1;
			//verifica se o mon�mio tem um sinal negativo
			if(monomio.charAt(0)== '-'){
				return parcial(monomio.substring(1), x) *  -1;
			}
			//verifica se o mon�mio tem icognita e se ela tem indice multiplicativo
			if( monomio.indexOf('x') > 0 & monomio.indexOf('x') != 0){
				String num = monomio.substring(0 ,monomio.indexOf('x') );
				numero = Integer.parseInt(num);
			}
			//verifica se o monomio tem expoente
			if(monomio.indexOf('x') < monomio.length() - 1 & monomio.indexOf('x') >= 0 ){
				String exp = monomio.substring(monomio.indexOf('x')+1);
				expoente =Integer.parseInt(exp);
			}
			//verifica se o monomio � so uma constante
			if(monomio.indexOf('x') < 0){
				numero = Integer.parseInt(monomio);
				return numero;
			}
		
			
			resultado = numero * Math.pow(x, expoente);
			
		}catch(Exception e){}
		return resultado; 
	}
	/**
     * Imprime uma equa��o polinomial
     * 
     *
     * @param  
     * @param 
     * @return um String contendo a equa��o polinomial 
     */
	public String printEquacao(){
		return this.equacao;
	}
	/**
     * Calcula o grau de uma equa��o polinomial
     * j� existente
     *
     * @param  monomio uma equa��o polinomial
     * @return o grau da Equa��o polinomial
     */
	public static int calculaGrau(String monomio){
		try{
			if(monomio.indexOf('x') >= 0 && monomio.indexOf('x')+1 < monomio.length()){
				return  Integer.parseInt(monomio.substring(monomio.indexOf('x')+1));
			}
			else if (monomio.indexOf('x') >= 0 && monomio.indexOf('x')+1 == monomio.length()){
				return 1;
			}
		}catch(Exception e){}	
			return 0;
			
	}
	
	
		
		
	
}