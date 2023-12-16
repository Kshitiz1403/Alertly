export class Result<T> {
  private success: boolean;
  private data: T;

  private constructor(isSuccess: boolean, value?: T) {
    this.success = isSuccess;
    this.data = value;

    Object.freeze(this);
  }
  public getValue(): T {
    if (!this.success) {
      throw new Error(`Cant retrieve the value from a failed result.`);
    }
    return this.data;
  }

  public static success<U>(value?: U): Result<U> {
    return new Result<U>(true, value);
  }

  public static error<U>(e: any): Result<U> {
    return new Result<U>(false, e.message || e);
  }

  public static combine(results: Result<any>[]): Result<any> {
    for (let result of results) {
      if (!result.success) return result;
    }
    return Result.success<any>();
  }
}
