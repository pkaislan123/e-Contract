/*
 * Decompiled with CFR 0.151.
 */
package main.java.cadastros;

public class CadastroFuncionarioEvento {
    private int id_evento;
    private int id_colaborador;
    private int id_contrato;
    private int tipo_evento;
    private int motivo_demissao;
    private int movimentacao;
    private String data_folga;
    private String data_evento;
    private String data_ferias_ida;
    private String data_ferias_volta;
    private String hora_saida;
    private String hora_entrada;
    private String data_saida;
    private double novo_valor_salarial;

    public int getId_evento() {
        return this.id_evento;
    }

    public int getId_colaborador() {
        return this.id_colaborador;
    }

    public int getId_contrato() {
        return this.id_contrato;
    }

    public int getTipo_evento() {
        return this.tipo_evento;
    }

    public int getMotivo_demissao() {
        return this.motivo_demissao;
    }

    public int getMovimentacao() {
        return this.movimentacao;
    }

    public String getData_folga() {
        return this.data_folga;
    }

    public String getData_evento() {
        return this.data_evento;
    }

    public String getData_ferias_ida() {
        return this.data_ferias_ida;
    }

    public String getData_ferias_volta() {
        return this.data_ferias_volta;
    }

    public String getHora_saida() {
        return this.hora_saida;
    }

    public String getHora_entrada() {
        return this.hora_entrada;
    }

    public String getData_saida() {
        return this.data_saida;
    }

    public double getNovo_valor_salarial() {
        return this.novo_valor_salarial;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public void setId_colaborador(int id_colaborador) {
        this.id_colaborador = id_colaborador;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public void setTipo_evento(int tipo_evento) {
        this.tipo_evento = tipo_evento;
    }

    public void setMotivo_demissao(int motivo_demissao) {
        this.motivo_demissao = motivo_demissao;
    }

    public void setMovimentacao(int movimentacao) {
        this.movimentacao = movimentacao;
    }

    public void setData_folga(String data_folga) {
        this.data_folga = data_folga;
    }

    public void setData_evento(String data_evento) {
        this.data_evento = data_evento;
    }

    public void setData_ferias_ida(String data_ferias_ida) {
        this.data_ferias_ida = data_ferias_ida;
    }

    public void setData_ferias_volta(String data_ferias_volta) {
        this.data_ferias_volta = data_ferias_volta;
    }

    public void setHora_saida(String hora_saida) {
        this.hora_saida = hora_saida;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public void setData_saida(String data_saida) {
        this.data_saida = data_saida;
    }

    public void setNovo_valor_salarial(double novo_valor_salarial) {
        this.novo_valor_salarial = novo_valor_salarial;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CadastroFuncionarioEvento)) {
            return false;
        }
        CadastroFuncionarioEvento other = (CadastroFuncionarioEvento)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId_evento() != other.getId_evento()) {
            return false;
        }
        if (this.getId_colaborador() != other.getId_colaborador()) {
            return false;
        }
        if (this.getId_contrato() != other.getId_contrato()) {
            return false;
        }
        if (this.getTipo_evento() != other.getTipo_evento()) {
            return false;
        }
        if (this.getMotivo_demissao() != other.getMotivo_demissao()) {
            return false;
        }
        if (this.getMovimentacao() != other.getMovimentacao()) {
            return false;
        }
        if (Double.compare(this.getNovo_valor_salarial(), other.getNovo_valor_salarial()) != 0) {
            return false;
        }
        String this$data_folga = this.getData_folga();
        String other$data_folga = other.getData_folga();
        if (this$data_folga == null ? other$data_folga != null : !this$data_folga.equals(other$data_folga)) {
            return false;
        }
        String this$data_evento = this.getData_evento();
        String other$data_evento = other.getData_evento();
        if (this$data_evento == null ? other$data_evento != null : !this$data_evento.equals(other$data_evento)) {
            return false;
        }
        String this$data_ferias_ida = this.getData_ferias_ida();
        String other$data_ferias_ida = other.getData_ferias_ida();
        if (this$data_ferias_ida == null ? other$data_ferias_ida != null : !this$data_ferias_ida.equals(other$data_ferias_ida)) {
            return false;
        }
        String this$data_ferias_volta = this.getData_ferias_volta();
        String other$data_ferias_volta = other.getData_ferias_volta();
        if (this$data_ferias_volta == null ? other$data_ferias_volta != null : !this$data_ferias_volta.equals(other$data_ferias_volta)) {
            return false;
        }
        String this$hora_saida = this.getHora_saida();
        String other$hora_saida = other.getHora_saida();
        if (this$hora_saida == null ? other$hora_saida != null : !this$hora_saida.equals(other$hora_saida)) {
            return false;
        }
        String this$hora_entrada = this.getHora_entrada();
        String other$hora_entrada = other.getHora_entrada();
        if (this$hora_entrada == null ? other$hora_entrada != null : !this$hora_entrada.equals(other$hora_entrada)) {
            return false;
        }
        String this$data_saida = this.getData_saida();
        String other$data_saida = other.getData_saida();
        return !(this$data_saida == null ? other$data_saida != null : !this$data_saida.equals(other$data_saida));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CadastroFuncionarioEvento;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId_evento();
        result = result * 59 + this.getId_colaborador();
        result = result * 59 + this.getId_contrato();
        result = result * 59 + this.getTipo_evento();
        result = result * 59 + this.getMotivo_demissao();
        result = result * 59 + this.getMovimentacao();
        long $novo_valor_salarial = Double.doubleToLongBits(this.getNovo_valor_salarial());
        result = result * 59 + (int)($novo_valor_salarial ^ $novo_valor_salarial >>> 32);
        String $data_folga = this.getData_folga();
        result = result * 59 + ($data_folga == null ? 43 : $data_folga.hashCode());
        String $data_evento = this.getData_evento();
        result = result * 59 + ($data_evento == null ? 43 : $data_evento.hashCode());
        String $data_ferias_ida = this.getData_ferias_ida();
        result = result * 59 + ($data_ferias_ida == null ? 43 : $data_ferias_ida.hashCode());
        String $data_ferias_volta = this.getData_ferias_volta();
        result = result * 59 + ($data_ferias_volta == null ? 43 : $data_ferias_volta.hashCode());
        String $hora_saida = this.getHora_saida();
        result = result * 59 + ($hora_saida == null ? 43 : $hora_saida.hashCode());
        String $hora_entrada = this.getHora_entrada();
        result = result * 59 + ($hora_entrada == null ? 43 : $hora_entrada.hashCode());
        String $data_saida = this.getData_saida();
        result = result * 59 + ($data_saida == null ? 43 : $data_saida.hashCode());
        return result;
    }

    public String toString() {
        return "CadastroFuncionarioEvento(id_evento=" + this.getId_evento() + ", id_colaborador=" + this.getId_colaborador() + ", id_contrato=" + this.getId_contrato() + ", tipo_evento=" + this.getTipo_evento() + ", motivo_demissao=" + this.getMotivo_demissao() + ", movimentacao=" + this.getMovimentacao() + ", data_folga=" + this.getData_folga() + ", data_evento=" + this.getData_evento() + ", data_ferias_ida=" + this.getData_ferias_ida() + ", data_ferias_volta=" + this.getData_ferias_volta() + ", hora_saida=" + this.getHora_saida() + ", hora_entrada=" + this.getHora_entrada() + ", data_saida=" + this.getData_saida() + ", novo_valor_salarial=" + this.getNovo_valor_salarial() + ")";
    }

    public CadastroFuncionarioEvento(int id_evento, int id_colaborador, int id_contrato, int tipo_evento, int motivo_demissao, int movimentacao, String data_folga, String data_evento, String data_ferias_ida, String data_ferias_volta, String hora_saida, String hora_entrada, String data_saida, double novo_valor_salarial) {
        this.id_evento = id_evento;
        this.id_colaborador = id_colaborador;
        this.id_contrato = id_contrato;
        this.tipo_evento = tipo_evento;
        this.motivo_demissao = motivo_demissao;
        this.movimentacao = movimentacao;
        this.data_folga = data_folga;
        this.data_evento = data_evento;
        this.data_ferias_ida = data_ferias_ida;
        this.data_ferias_volta = data_ferias_volta;
        this.hora_saida = hora_saida;
        this.hora_entrada = hora_entrada;
        this.data_saida = data_saida;
        this.novo_valor_salarial = novo_valor_salarial;
    }

    public CadastroFuncionarioEvento() {
    }
}

