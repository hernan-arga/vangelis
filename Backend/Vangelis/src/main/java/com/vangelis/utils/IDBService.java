package com.vangelis.utils;

public interface IDBService {

    //IQueryable<T> Query<T>() where T : class;

    public <T> void Save(T objectT);

    /*void SaveList<T>(IEnumerable<T> objectListT) where T : class;

    void SaveBatchList<T>(IEnumerable<T> objectListT) where T : class;

    IQuery GetNamedQuery(string namedQuery);

    void Delete<T>(T objectT) where T : class;

    void DeleteList<T>(IEnumerable<T> objectListT) where T : class;

    ISqlQuery<TResult> CreateSpQuery<TResult>(string storedProcedureName);

    ISqlQuery<TResult2> CreateSqlQuery<TResult2>(string queryString);

    void EndSession(bool rollback);

    void BeginSession();

    void CommitTransactions();

    void RollbackTransactions();*/
}
