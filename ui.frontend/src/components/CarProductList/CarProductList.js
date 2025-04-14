import React, { useEffect, useState } from 'react';
import { MapTo } from '@adobe/aem-react-editable-components';
import './CarProductList.css'; // We'll add styles here next

const API_URL = "https://script.googleusercontent.com/macros/echo?user_content_key=AehSKLj5I6aKhvv0_gps5Zk-273YWVtZxYzTGPzgL4l2mZddBABGgiL1ntMGz5ss5gslkFlboJEhnok4Q991mQOywueONZttm4Xmw12VBVAtNgcWXZHxAmi-Zyb41XqpHPnrZSSdyoOwdb_1nSKT-GUp6kqFlZDAVWuTWovaaSEZK7QVBEMDlxK87Uijo7guYt5rnjn9hawrXzmPUSPl3jRl2P2wd9jBWn-GxwGNmf5ea4zKTfPi00ip3_kKk5WuFyHMn-GPIQglBnGoKa4F1aadSt7RNeiZlQ&lib=MH2bqY40uIuCSgMGanxyBiEbMvH4Ype3g";

const CarProductList = ({ cqPath }) => {
  const [cars, setCars] = useState([]);

  useEffect(() => {
    fetch(API_URL)
      .then((res) => res.json())
      .then((data) => setCars(data))
      .catch((err) => console.error("Error fetching car data:", err));
  }, []);

  if (!cars || cars.length === 0) {
    return <div className="p-4 text-center">Loading car products...</div>;
  }

  return (
    <div className="car-product-list">
  {cars.map((car) => (
    <div className="car-product-row">
      <div className="car-left">
        <img src={car["Image URL"]} alt={car["Product Name"]} />
        <h2>{car["Product Name"]}</h2>
      </div>
      <div className="car-right">
        <p>${car.Price}</p>
        <p>{car.Description}</p>
      </div>
    </div>
  ))}
</div>
  );
};

export default MapTo("wknd-spa-react/components/carProductList")(CarProductList);
