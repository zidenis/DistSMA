/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables.records;


import br.unb.sma.database.tables.TDenParteFaseProc;
import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Date;


/**
 * Denominacao das partes do processo em uma determinada frase. Relaciona 
 * as tabela T_FASE_PROCESSUAL com T_PROCESSO_PARTE de forma a indicar qual 
 * a forma de atuacao da parte na fase processual determinada.
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TDenParteFaseProcRecord extends UpdatableRecordImpl<TDenParteFaseProcRecord> implements Record5<Long, Long, Date, String, Boolean> {

	private static final long serialVersionUID = 341007091;

	/**
	 * Create a detached TDenParteFaseProcRecord
	 */
	public TDenParteFaseProcRecord() {
		super(TDenParteFaseProc.T_DEN_PARTE_FASE_PROC);
	}

	/**
	 * Create a detached, initialised TDenParteFaseProcRecord
	 */
	public TDenParteFaseProcRecord(Long codProcesso, Long codParte, Date dtaInicioFase, String sigClasse, Boolean indPoloAtivo) {
		super(TDenParteFaseProc.T_DEN_PARTE_FASE_PROC);

		setValue(0, codProcesso);
		setValue(1, codParte);
		setValue(2, dtaInicioFase);
		setValue(3, sigClasse);
		setValue(4, indPoloAtivo);
	}

	/**
	 * Getter for <code>procjud.t_den_parte_fase_proc.cod_processo</code>. Codigo numerico atribuido ao processo para identifica-lo no banco de dados
	 */
	public Long getCodProcesso() {
		return (Long) getValue(0);
	}

	/**
	 * Setter for <code>procjud.t_den_parte_fase_proc.cod_processo</code>. Codigo numerico atribuido ao processo para identifica-lo no banco de dados
	 */
	public void setCodProcesso(Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>procjud.t_den_parte_fase_proc.cod_parte</code>. Codigo identificador da parte
	 */
	public Long getCodParte() {
		return (Long) getValue(1);
	}

	/**
	 * Setter for <code>procjud.t_den_parte_fase_proc.cod_parte</code>. Codigo identificador da parte
	 */
	public void setCodParte(Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>procjud.t_den_parte_fase_proc.dta_inicio_fase</code>. Data de inicio da fase
	 */
	public Date getDtaInicioFase() {
		return (Date) getValue(2);
	}

	/**
	 * Setter for <code>procjud.t_den_parte_fase_proc.dta_inicio_fase</code>. Data de inicio da fase
	 */
	public void setDtaInicioFase(Date value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>procjud.t_den_parte_fase_proc.sig_classe</code>. Sigla identificadora da classe processual para a respectiva fase
	 */
	public String getSigClasse() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>procjud.t_den_parte_fase_proc.sig_classe</code>. Sigla identificadora da classe processual para a respectiva fase
	 */
	public void setSigClasse(String value) {
		setValue(3, value);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * Getter for <code>procjud.t_den_parte_fase_proc.ind_polo_ativo</code>.
	 */
	public Boolean getIndPoloAtivo() {
		return (Boolean) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * Setter for <code>procjud.t_den_parte_fase_proc.ind_polo_ativo</code>.
	 */
	public void setIndPoloAtivo(Boolean value) {
		setValue(4, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record4<Long, Long, Date, String> key() {
		return (Record4) super.key();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Long, Long, Date, String, Boolean> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Long, Long, Date, String, Boolean> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return TDenParteFaseProc.T_DEN_PARTE_FASE_PROC.COD_PROCESSO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return TDenParteFaseProc.T_DEN_PARTE_FASE_PROC.COD_PARTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field3() {
		return TDenParteFaseProc.T_DEN_PARTE_FASE_PROC.DTA_INICIO_FASE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return TDenParteFaseProc.T_DEN_PARTE_FASE_PROC.SIG_CLASSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field5() {
		return TDenParteFaseProc.T_DEN_PARTE_FASE_PROC.IND_POLO_ATIVO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getCodProcesso();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value2() {
		return getCodParte();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value3() {
		return getDtaInicioFase();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getSigClasse();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value5() {
		return getIndPoloAtivo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDenParteFaseProcRecord value1(Long value) {
		setCodProcesso(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDenParteFaseProcRecord value2(Long value) {
		setCodParte(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDenParteFaseProcRecord value3(Date value) {
		setDtaInicioFase(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDenParteFaseProcRecord value4(String value) {
		setSigClasse(value);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDenParteFaseProcRecord value5(Boolean value) {
		setIndPoloAtivo(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDenParteFaseProcRecord values(Long value1, Long value2, Date value3, String value4, Boolean value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}
}
