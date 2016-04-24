/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TMotivoRedistribuicaoRecord;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Relaciona os possiveis motivos para redistribuicao de um processo
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TMotivoRedistribuicao extends TableImpl<TMotivoRedistribuicaoRecord> {

	/**
	 * The reference instance of <code>procjud.t_motivo_redistribuicao</code>
	 */
	public static final TMotivoRedistribuicao T_MOTIVO_REDISTRIBUICAO = new TMotivoRedistribuicao();
	private static final long serialVersionUID = 2029029267;
	/**
	 * The column <code>procjud.t_motivo_redistribuicao.cod_motivo_redist</code>. Codigo do motivo da redistribuicao
	 */
	public final TableField<TMotivoRedistribuicaoRecord, Byte> COD_MOTIVO_REDIST = createField("cod_motivo_redist", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "Codigo do motivo da redistribuicao");
	/**
	 * The column <code>procjud.t_motivo_redistribuicao.des_motivo_redist</code>. Relaciona os possiveis motivos para redistribuicao do processo
	 */
	public final TableField<TMotivoRedistribuicaoRecord, String> DES_MOTIVO_REDIST = createField("des_motivo_redist", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "Relaciona os possiveis motivos para redistribuicao do processo");

	/**
	 * Create a <code>procjud.t_motivo_redistribuicao</code> table reference
	 */
	public TMotivoRedistribuicao() {
		this("t_motivo_redistribuicao", null);
	}

	/**
	 * Create an aliased <code>procjud.t_motivo_redistribuicao</code> table reference
	 */
	public TMotivoRedistribuicao(String alias) {
		this(alias, T_MOTIVO_REDISTRIBUICAO);
	}

	private TMotivoRedistribuicao(String alias, Table<TMotivoRedistribuicaoRecord> aliased) {
		this(alias, aliased, null);
	}

	private TMotivoRedistribuicao(String alias, Table<TMotivoRedistribuicaoRecord> aliased, Field<?>[] parameters) {
		super(alias, Procjud.PROCJUD, aliased, parameters, "Relaciona os possiveis motivos para redistribuicao de um processo");
	}

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TMotivoRedistribuicaoRecord> getRecordType() {
		return TMotivoRedistribuicaoRecord.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TMotivoRedistribuicaoRecord> getPrimaryKey() {
		return Keys.MOTIVO_REDISTRIBUICAO_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TMotivoRedistribuicaoRecord>> getKeys() {
		return Arrays.<UniqueKey<TMotivoRedistribuicaoRecord>>asList(Keys.MOTIVO_REDISTRIBUICAO_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TMotivoRedistribuicao as(String alias) {
		return new TMotivoRedistribuicao(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TMotivoRedistribuicao rename(String name) {
		return new TMotivoRedistribuicao(name, null);
	}
}
