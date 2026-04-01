const API_URL = "http://localhost:8080/api/order/process";

const form = document.getElementById("orderForm");
const priceInput = document.getElementById("price");
const discountTypeSelect = document.getElementById("discountType");
const submitBtn = document.getElementById("submitBtn");
const errorBox = document.getElementById("errorBox");

const resultCard = document.getElementById("resultCard");
const statusPill = document.getElementById("statusPill");
const giaBanDauEl = document.getElementById("giaBanDau");
const giaSauGiamEl = document.getElementById("giaSauGiam");
const stepsEl = document.getElementById("steps");

function showError(message) {
  errorBox.textContent = message;
  errorBox.classList.add("show");
}

function clearError() {
  errorBox.textContent = "";
  errorBox.classList.remove("show");
}

function setLoading(isLoading) {
  submitBtn.disabled = isLoading;
  submitBtn.classList.toggle("loading", isLoading);

  statusPill.classList.remove("ok", "err", "loading");
  statusPill.classList.add(isLoading ? "loading" : "");
  statusPill.textContent = isLoading ? "Đang xử lý..." : "Chưa xử lý";
}

function moneyVND(value) {
  const num = Number(value);
  if (!Number.isFinite(num)) return "—";
  return new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(num);
}

function resetStepsToPending() {
  const items = stepsEl.querySelectorAll(".step");
  items.forEach((li) => {
    li.classList.remove("done", "flash");
    li.classList.add("pending");
  });
}

async function animateSteps(steps) {
  resetStepsToPending();

  const items = Array.from(stepsEl.querySelectorAll(".step"));
  const byText = new Map(items.map((li) => [li.textContent.replace(/^✔\s*/, "").trim(), li]));

  for (const stepText of steps) {
    const li = byText.get(stepText);
    if (!li) continue;
    await new Promise((r) => setTimeout(r, 220));
    li.classList.remove("pending");
    li.classList.add("done", "flash");
  }
}

function parsePrice(raw) {
  const normalized = String(raw).trim().replace(",", ".");
  const value = Number(normalized);
  return Number.isFinite(value) ? value : NaN;
}

async function processOrder({ price, discountType }) {
  const res = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ price, discountType }),
  });

  const data = await res.json().catch(() => null);
  if (!res.ok) {
    const msg =
      data?.message ||
      data?.errors?.price ||
      data?.errors?.discountType ||
      "Có lỗi xảy ra khi xử lý đơn hàng.";
    throw new Error(msg);
  }
  return data;
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();
  clearError();

  const price = parsePrice(priceInput.value);
  const discountType = discountTypeSelect.value;

  if (!Number.isFinite(price) || price <= 0) {
    showError("Vui lòng nhập giá đơn hàng là số > 0.");
    priceInput.focus();
    return;
  }

  setLoading(true);
  resetStepsToPending();

  try {
    const result = await processOrder({ price, discountType });

    statusPill.classList.remove("loading", "err");
    statusPill.classList.add("ok");
    statusPill.textContent = "Xử lý thành công";

    giaBanDauEl.textContent = moneyVND(result.giaBanDau);
    giaSauGiamEl.textContent = moneyVND(result.giaSauGiam);

    resultCard.classList.remove("highlight");
    void resultCard.offsetWidth;
    resultCard.classList.add("highlight");

    await animateSteps(result.cacBuoc || []);
  } catch (err) {
    statusPill.classList.remove("loading", "ok");
    statusPill.classList.add("err");
    statusPill.textContent = "Xử lý thất bại";
    showError(err?.message || "Không thể xử lý đơn hàng.");
  } finally {
    setLoading(false);
  }
});

