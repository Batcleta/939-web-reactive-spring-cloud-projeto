package tech.ada.pagamento.Exception;

public class GetSomeMoney extends RuntimeException {
    public GetSomeMoney(String message) {
        super(message);
    }
}