package com.thesis.yuema.common;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.DefaultComponentSafeNamingStrategy;

public class DatabaseNamingStrategy extends DefaultComponentSafeNamingStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8802629236682463322L;
	private Boolean isAddUnderscores;// 是否以下划线形式命名
	private Integer maxLength;// 命名最大长度限制

	protected static String addUnderscores(String name) {
		if (name == null) {
			return null;
		}
		StringBuffer stringBuffer = new StringBuffer(name.replace('.', '_'));
		for (int i = 1; i < stringBuffer.length() - 1; i++) {
			if (Character.isLowerCase(stringBuffer.charAt(i - 1))
					&& Character.isUpperCase(stringBuffer.charAt(i))
					&& Character.isLowerCase(stringBuffer.charAt(i + 1))) {
				stringBuffer.insert(i++, '_');
			}
		}
		return stringBuffer.toString().toLowerCase();
	}

	@Override
	public String classToTableName(String className) {
		String tableName;
		if (isAddUnderscores) {
			tableName = StringUtils.substring(
					super.classToTableName(addUnderscores(className)), 0,
					maxLength);
		} else {
			tableName = StringUtils.substring(
					super.classToTableName(className), 0, maxLength);
		}
		return tableName;
	}

	@Override
	public String collectionTableName(String ownerEntity,
			String ownerEntityTable, String associatedEntity,
			String associatedEntityTable, String propertyName) {
		String collectionTableName;
		if (isAddUnderscores) {
			collectionTableName = StringUtils.substring(super
					.collectionTableName(addUnderscores(ownerEntity),
							addUnderscores(ownerEntityTable),
							addUnderscores(associatedEntity),
							addUnderscores(associatedEntityTable),
							addUnderscores(propertyName)), 0, maxLength);
		} else {
			collectionTableName = StringUtils.substring(super
					.collectionTableName(ownerEntity, ownerEntityTable,
							associatedEntity, associatedEntityTable,
							propertyName), 0, maxLength);
		}
		return collectionTableName;
	}

	@Override
	public String logicalCollectionTableName(String tableName,
			String ownerEntityTable, String associatedEntityTable,
			String propertyName) {
		String logicalCollectionTableName;
		if (isAddUnderscores) {
			logicalCollectionTableName = StringUtils.substring(super
					.logicalCollectionTableName(addUnderscores(tableName),
							addUnderscores(ownerEntityTable),
							addUnderscores(associatedEntityTable),
							addUnderscores(propertyName)), 0, maxLength);
		} else {
			logicalCollectionTableName = StringUtils.substring(super
					.logicalCollectionTableName(tableName, ownerEntityTable,
							associatedEntityTable, propertyName), 0, maxLength);
		}
		return logicalCollectionTableName;
	}

	@Override
	public String propertyToColumnName(String propertyName) {
		if (isAddUnderscores) {
			return StringUtils.substring(
					super.propertyToColumnName(addUnderscores(propertyName)),
					0, maxLength);
		} else {
			return StringUtils.substring(
					super.propertyToColumnName(propertyName), 0, maxLength);
		}
	}

	@Override
	public String foreignKeyColumnName(String propertyName,
			String propertyEntityName, String propertyTableName,
			String referencedColumnName) {
		if (isAddUnderscores) {
			return StringUtils.substring(super.foreignKeyColumnName(
					addUnderscores(propertyName),
					addUnderscores(propertyEntityName),
					addUnderscores(propertyTableName),
					addUnderscores(referencedColumnName)), 0, maxLength);
		} else {
			return StringUtils.substring(super.foreignKeyColumnName(
					propertyName, propertyEntityName, propertyTableName,
					referencedColumnName), 0, maxLength);
		}
	}

	@Override
	public String logicalCollectionColumnName(String columnName,
			String propertyName, String referencedColumn) {
		if (isAddUnderscores) {
			return StringUtils.substring(super.logicalCollectionColumnName(
					addUnderscores(columnName), addUnderscores(propertyName),
					addUnderscores(referencedColumn)), 0, maxLength);
		} else {
			return StringUtils.substring(super.logicalCollectionColumnName(
					columnName, propertyName, referencedColumn), 0, maxLength);
		}
	}

	@Override
	public String logicalColumnName(String columnName, String propertyName) {
		if (isAddUnderscores) {
			return StringUtils.substring(super.logicalColumnName(
					addUnderscores(columnName), addUnderscores(propertyName)),
					0, maxLength);
		} else {
			return StringUtils.substring(
					super.logicalColumnName(columnName, propertyName), 0,
					maxLength);
		}
	}

	@Override
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		if (isAddUnderscores) {
			return StringUtils.substring(super.joinKeyColumnName(
					addUnderscores(joinedColumn), addUnderscores(joinedTable)),
					0, maxLength);
		} else {
			return StringUtils.substring(
					super.joinKeyColumnName(joinedColumn, joinedTable), 0,
					maxLength);
		}
	}

	public Boolean getIsAddUnderscores() {
		return isAddUnderscores;
	}

	public void setIsAddUnderscores(Boolean isAddUnderscores) {
		this.isAddUnderscores = isAddUnderscores;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

}