/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables.records;


import br.unb.sma.database.tables.TCompetencia;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


/**
 * Mapeamento entre as Classes Processuais e os Orgaos Judicantes para onde 
 * processos da classe podem ser distribuidos
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TCompetenciaRecord extends UpdatableRecordImpl<TCompetenciaRecord> implements Record2<String, String> {

	private static final long serialVersionUID = -1052773093;

	/**
	 * Create a detached TCompetenciaRecord
	 */
	public TCompetenciaRecord() {
		super(TCompetencia.T_COMPETENCIA);
	}

	/**
	 * Create a detached, initialised TCompetenciaRecord
	 */
	public TCompetenciaRecord(String sigClasse, String sigOj) {
		super(TCompetencia.T_COMPETENCIA);

		setValue(0, sigClasse);
		setValue(1, sigOj);
	}

	/**
	 * Getter for <code>procjud.t_competencia.sig_classe</code>. Sigla identificadora da classe
	 */
	public String getSigClasse() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>procjud.t_competencia.sig_classe</code>. Sigla identificadora da classe
	 */
	public void setSigClasse(String value) {
		setValue(0, value);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * Getter for <code>procjud.t_competencia.sig_oj</code>.
	 */
	public String getSigOj() {
		return (String) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * Setter for <code>procjud.t_competencia.sig_oj</code>.
	 */
	public void setSigOj(String value) {
		setValue(1, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record2<String, String> key() {
		return (Record2) super.key();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<String, String> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<String, String> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return TCompetencia.T_COMPETENCIA.SIG_CLASSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return TCompetencia.T_COMPETENCIA.SIG_OJ;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getSigClasse();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getSigOj();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TCompetenciaRecord value1(String value) {
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
	public TCompetenciaRecord value2(String value) {
		setSigOj(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TCompetenciaRecord values(String value1, String value2) {
		value1(value1);
		value2(value2);
		return this;
	}
}
