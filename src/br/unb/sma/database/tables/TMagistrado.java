/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TMagistradoRecord;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Magistrados que atuam em Orgaos Judicantes para julgar processos
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TMagistrado extends TableImpl<TMagistradoRecord> {

	/**
	 * The reference instance of <code>procjud.t_magistrado</code>
	 */
	public static final TMagistrado T_MAGISTRADO = new TMagistrado();
	private static final long serialVersionUID = 481378534;
	/**
	 * The column <code>procjud.t_magistrado.cod_magistrado</code>. Codigo Identificador do Magistrado
	 */
	public final TableField<TMagistradoRecord, String> COD_MAGISTRADO = createField("cod_magistrado", org.jooq.impl.SQLDataType.VARCHAR.length(4).nullable(false), this, "Codigo Identificador do Magistrado");
	/**
	 * The column <code>procjud.t_magistrado.nom_magistrado</code>. Nome Completo do Magistrado
	 */
	public final TableField<TMagistradoRecord, String> NOM_MAGISTRADO = createField("nom_magistrado", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "Nome Completo do Magistrado");
	/**
	 * The column <code>procjud.t_magistrado.ind_administracao</code>. identifica se o magistrado é membro da administração do tribunal (presidente, vice e corregedor)
	 */
	public final TableField<TMagistradoRecord, Boolean> IND_ADMINISTRACAO = createField("ind_administracao", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaulted(true), this, "identifica se o magistrado é membro da administração do tribunal (presidente, vice e corregedor)");

	/**
	 * Create a <code>procjud.t_magistrado</code> table reference
	 */
	public TMagistrado() {
		this("t_magistrado", null);
	}

	/**
	 * Create an aliased <code>procjud.t_magistrado</code> table reference
	 */
	public TMagistrado(String alias) {
		this(alias, T_MAGISTRADO);
	}

	private TMagistrado(String alias, Table<TMagistradoRecord> aliased) {
		this(alias, aliased, null);
	}

	private TMagistrado(String alias, Table<TMagistradoRecord> aliased, Field<?>[] parameters) {
		super(alias, Procjud.PROCJUD, aliased, parameters, "Magistrados que atuam em Orgaos Judicantes para julgar processos");
	}

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TMagistradoRecord> getRecordType() {
		return TMagistradoRecord.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TMagistradoRecord> getPrimaryKey() {
		return Keys.MAGISTRADO_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TMagistradoRecord>> getKeys() {
		return Arrays.<UniqueKey<TMagistradoRecord>>asList(Keys.MAGISTRADO_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TMagistrado as(String alias) {
		return new TMagistrado(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TMagistrado rename(String name) {
		return new TMagistrado(name, null);
	}
}
