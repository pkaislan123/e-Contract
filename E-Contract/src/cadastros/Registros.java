package cadastros;

public class Registros {

	public static class RegistroCarregamento {

		double quantidade_total = 0;
		double quantidade_carregada = 0;
		double quantidade_restante = 0;
		double quantidade_total_nf = 0;
		double quantidade_restante_nf = 0;

		public double getQuantidade_total() {
			return quantidade_total;
		}

		public void setQuantidade_total(double quantidade_total) {
			this.quantidade_total = quantidade_total;
		}

		public double getQuantidade_carregada() {
			return quantidade_carregada;
		}

		public void setQuantidade_carregada(double quantidade_carregada) {
			this.quantidade_carregada = quantidade_carregada;
		}

		public double getQuantidade_restante() {
			return quantidade_restante;
		}

		public void setQuantidade_restante(double quantidade_restante) {
			this.quantidade_restante = quantidade_restante;
		}

		public double getQuantidade_total_nf() {
			return quantidade_total_nf;
		}

		public void setQuantidade_total_nf(double quantidade_total_nf) {
			this.quantidade_total_nf = quantidade_total_nf;
		}

		public double getQuantidade_restante_nf() {
			return quantidade_restante_nf;
		}

		public void setQuantidade_restante_nf(double quantidade_restante_nf) {
			this.quantidade_restante_nf = quantidade_restante_nf;
		}

	}

	public static class RegistroPagamento {

		double valor_total_pagamentos = 0;
		double valor_total_pagamentos_efetuados = 0;
		double valor_total_transferencias_retiradas = 0;
		double valor_total_transferencias_recebidas = 0;
		double valor_total_pagamentos_restantes = 0;

		public double getValor_total_pagamentos() {
			return valor_total_pagamentos;
		}

		public void setValor_total_pagamentos(double valor_total_pagamentos) {
			this.valor_total_pagamentos = valor_total_pagamentos;
		}

		public double getValor_total_pagamentos_efetuados() {
			return valor_total_pagamentos_efetuados;
		}

		public void setValor_total_pagamentos_efetuados(double valor_total_pagamentos_efetuados) {
			this.valor_total_pagamentos_efetuados = valor_total_pagamentos_efetuados;
		}

		public double getValor_total_transferencias_retiradas() {
			return valor_total_transferencias_retiradas;
		}

		public void setValor_total_transferencias_retiradas(double valor_total_transferencias_retiradas) {
			this.valor_total_transferencias_retiradas = valor_total_transferencias_retiradas;
		}

		public double getValor_total_transferencias_recebidas() {
			return valor_total_transferencias_recebidas;
		}

		public void setValor_total_transferencias_recebidas(double valor_total_transferencias_recebidas) {
			this.valor_total_transferencias_recebidas = valor_total_transferencias_recebidas;
		}

		public double getValor_total_pagamentos_restantes() {
			return valor_total_pagamentos_restantes;
		}

		public void setValor_total_pagamentos_restantes(double valor_total_pagamentos_restantes) {
			this.valor_total_pagamentos_restantes = valor_total_pagamentos_restantes;
		}

	}

}
