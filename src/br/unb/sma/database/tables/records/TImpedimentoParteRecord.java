/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables.records;


import br.unb.sma.database.tables.TImpedimentoParte;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Date;


/**
 * Impedimento de Magistrados em relacao as Partes de processos
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TImpedimentoParteRecord extends UpdatableRecordImpl<TImpedimentoParteRecord> implements Record3<String, Long, Date> {

	private static final long serialVersionUID = -366780192;

	/**
	 * Create a detached TImpedimentoParteRecord
	 */
	public TImpedimentoParteRecord() {
		super(TImpedimentoParte.T_IMPEDIMENTO_PARTE);
	}

	/**
	 * Create a detached, initialised TImpedimentoParteRecord
	 */
	public TImpedimentoParteRecord(String codMagistrado, Long codParte, Date dtaRegistro) {
		super(TImpedimentoParte.T_IMPEDIMENTO_PARTE);

		setValue(0, codMagistrado);
		setValue(1, codParte);
		setValue(2, dtaRegistro);
	}

	/**
	 * Getter for <code>procjud.t_impedimento_parte.cod_magistrado</code>. Codigo Identificador do Magistrado
	 */
	public String getCodMagistrado() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>procjud.t_impedimento_parte.cod_magistrado</code>. Codigo Identificador do Magistrado
	 */
	public void setCodMagistrado(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>procjud.t_impedimento_parte.cod_parte</code>. Codigo identificador da parte
	 */
	public Long getCodParte() {
		return (Long) getValue(1);
	}

	/**
	 * Setter for <code>procjud.t_impedimento_parte.cod_parte</code>. Codigo identificador da parte
	 */
	public void setCodParte(Long value) {
		setValue(1, value);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * Getter for <code>procjud.t_impedimento_parte.dta_registro</code>. Data de registro do impedimento
	 */
	public Date getDtaRegistro() {
		return (Date) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * Setter for <code>procjud.t_impedimento_parte.dta_registro</code>. Data de registro do impedimento
	 */
	public void setDtaRegistro(Date value) {
		setValue(2, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record2<String, Long> key() {
		return (Record2) super.key();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, Long, Date> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, Long, Date> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return TImpedimentoParte.T_IMPEDIMENTO_PARTE.COD_MAGISTRADO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return TImpedimentoParte.T_IMPEDIMENTO_PARTE.COD_PARTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field3() {
		return TImpedimentoParte.T_IMPEDIMENTO_PARTE.DTA_REGISTRO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getCodMagistrado();
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
		return getDtaRegistro();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TImpedimentoParteRecord value1(String value) {
		setCodMagistrado(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TImpedimentoParteRecord value2(Long value) {
		setCodParte(value);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TImpedimentoParteRecord value3(Date value) {
		setDtaRegistro(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TImpedimentoParteRecord values(String value1, Long value2, Date value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}
}
