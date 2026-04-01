# DEMO Strategy + Template Method (Order Processing)

Ứng dụng web minh hoạ sự kết hợp giữa **Strategy Pattern** (khuyến mãi) và **Template Method Pattern** (quy trình xử lý đơn hàng).

## Cấu trúc thư mục

```
backend/
  src/main/java/com/demo/orderprocessing/
    controller/
    service/
    strategy/
    template/
frontend/
  index.html
  style.css
  script.js
```

## Yêu cầu

- Java 17+
- Maven 3.9+

## Chạy backend (Spring Boot)

Mở terminal tại thư mục `backend/` và chạy:

```bash
mvn -DskipTests package
java -jar target/order-processing-demo-0.0.1-SNAPSHOT.jar
```

Backend chạy tại `http://localhost:8080`.

> Ghi chú: trong một số môi trường Windows có đường dẫn chứa ký tự tiếng Việt, lệnh `mvn spring-boot:run` có thể gặp lỗi classpath. Cách chạy bằng `java -jar` ở trên là ổn định nhất cho demo.

### Test nhanh API

```bash
curl -X POST http://localhost:8080/api/order/process ^
  -H "Content-Type: application/json" ^
  -d "{\"price\":150000,\"discountType\":\"VIP\"}"
```

Kết quả trả về gồm:
- `giaBanDau`
- `giaSauGiam`
- `cacBuoc` (danh sách bước xử lý)

## Chạy frontend

### Cách nhanh nhất

Mở file `frontend/index.html` bằng trình duyệt.

### Cách khuyến nghị (serve tĩnh)

Tại thư mục `frontend/`:

```bash
python -m http.server 5500
```

Sau đó mở `http://localhost:5500`.

> Frontend gọi API `http://localhost:8080/api/order/process`. Backend đã bật CORS `*` để demo chạy được ngay.

## Gợi ý mở rộng Strategy

Thêm một loại khuyến mãi mới:

1. Tạo class mới implements `DiscountStrategy`
2. Trả về `type()` tương ứng trong `DiscountType`
3. Spring tự inject vào `DiscountStrategyRegistry` (không cần sửa if-else)

