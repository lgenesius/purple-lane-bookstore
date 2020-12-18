-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 18 Des 2020 pada 10.32
-- Versi server: 10.4.13-MariaDB
-- Versi PHP: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `purple_lane_bookstore`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `carts`
--

CREATE TABLE `carts` (
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `productQuantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `products`
--

CREATE TABLE `products` (
  `productId` int(11) NOT NULL,
  `productName` varchar(30) NOT NULL,
  `productAuthor` varchar(30) NOT NULL,
  `productPrice` int(11) NOT NULL,
  `productStock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `products`
--

INSERT INTO `products` (`productId`, `productName`, `productAuthor`, `productPrice`, `productStock`) VALUES
(1, 'Intelligent Saham', 'Marcus', 500000, 5),
(2, 'Smart English Version 2', 'James Larrington', 100000, 3),
(3, 'Business Marketing', 'Bill Thomas', 120000, 0),
(6, 'Marketing Smart', 'Luis', 512333, 3),
(7, 'Dongeng Fabel', 'Marzuki', 55000, 3),
(9, 'Buku Dagelan', 'Bob', 30000, 13),
(10, 'Buku BOBO', 'Bobo', 15000, 10);

-- --------------------------------------------------------

--
-- Struktur dari tabel `promos`
--

CREATE TABLE `promos` (
  `promoId` int(11) NOT NULL,
  `promoCode` varchar(30) NOT NULL,
  `promoDiscount` int(11) NOT NULL,
  `promoNote` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `promos`
--

INSERT INTO `promos` (`promoId`, `promoCode`, `promoDiscount`, `promoNote`) VALUES
(1, 'PURPLEBLESS', 30000, 'Code yang diberikan untuk perayaan perilisan aplikasi Purple Lane Bookstore.'),
(4, 'BOOKSTORE', 25000, 'Code bookstore'),
(6, 'BLESSBOOK', 15000, 'Bless book promo');

-- --------------------------------------------------------

--
-- Struktur dari tabel `roles`
--

CREATE TABLE `roles` (
  `roleId` int(11) NOT NULL,
  `roleName` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `roles`
--

INSERT INTO `roles` (`roleId`, `roleName`) VALUES
(2, 'Admin'),
(4, 'Customer'),
(1, 'Manager'),
(3, 'Promotion Team');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactions`
--

CREATE TABLE `transactions` (
  `transactionId` int(11) NOT NULL,
  `transactionDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `paymentType` varchar(30) NOT NULL,
  `cardNumber` varchar(50) NOT NULL,
  `promoCode` varchar(30) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transactions`
--

INSERT INTO `transactions` (`transactionId`, `transactionDate`, `paymentType`, `cardNumber`, `promoCode`, `userId`) VALUES
(4, '2020-12-17 03:37:45', 'Card Credit', '0987654321098765', 'BOOKSTORE', 27),
(5, '2020-12-17 04:30:30', 'Debit', '0987654321098765', 'BOOKSTORE', 27),
(6, '2020-12-17 09:14:55', 'Debit', '1234567890098765', 'PURPLEBLESS', 20),
(7, '2020-12-17 09:16:08', 'Debit', '1234567890123456', '', 20),
(8, '2020-12-17 11:53:12', 'Card Credit', '0912902109129021', 'PURPLEBLESS', 5),
(9, '2020-12-18 08:53:03', 'Debit', '1234567895678432', 'BOOKSTORE', 20);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaction_details`
--

CREATE TABLE `transaction_details` (
  `transactionId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `productQuantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaction_details`
--

INSERT INTO `transaction_details` (`transactionId`, `productId`, `productQuantity`) VALUES
(4, 7, 3),
(4, 9, 2),
(5, 7, 2),
(6, 6, 2),
(7, 3, 1),
(8, 3, 2),
(9, 2, 1),
(9, 7, 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`userId`, `roleId`, `username`, `password`) VALUES
(5, 4, 'test', 'test'),
(6, 4, 'hehe', 'hehe'),
(8, 4, 'huhu', 'hehe'),
(10, 4, 'gasd', 'gasd'),
(11, 4, 'testhehe', 'gasd'),
(12, 4, 'test123', 'test'),
(13, 4, 'a', 'hehe'),
(14, 4, 'test1234', 'test'),
(15, 4, 'test12345', 'test'),
(16, 4, 'lol', 'lol'),
(17, 2, 'admin1', 'admin1'),
(18, 3, 'promo team1', 'promo team1'),
(19, 1, 'manager1', 'manager1'),
(20, 4, 'user1', 'user1'),
(21, 2, 'admin2', 'admin2'),
(22, 3, 'promo team2', 'promo team2'),
(23, 3, 'promo team3', 'promo team3'),
(24, 2, 'admin3', 'admin3'),
(25, 2, 'admin4', 'admin4'),
(26, 2, 'admin5', 'admin5'),
(27, 4, 'user2', 'user2'),
(28, 4, 'user3', 'user3');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`userId`,`productId`),
  ADD KEY `productId` (`productId`);

--
-- Indeks untuk tabel `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`productId`);

--
-- Indeks untuk tabel `promos`
--
ALTER TABLE `promos`
  ADD PRIMARY KEY (`promoId`),
  ADD UNIQUE KEY `promoCode` (`promoCode`);

--
-- Indeks untuk tabel `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`roleId`),
  ADD UNIQUE KEY `roleName` (`roleName`);

--
-- Indeks untuk tabel `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transactionId`),
  ADD KEY `userId` (`userId`);

--
-- Indeks untuk tabel `transaction_details`
--
ALTER TABLE `transaction_details`
  ADD PRIMARY KEY (`transactionId`,`productId`),
  ADD KEY `productId` (`productId`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `roleId` (`roleId`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `products`
--
ALTER TABLE `products`
  MODIFY `productId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `promos`
--
ALTER TABLE `promos`
  MODIFY `promoId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `roles`
--
ALTER TABLE `roles`
  MODIFY `roleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transactionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `carts_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaction_details`
--
ALTER TABLE `transaction_details`
  ADD CONSTRAINT `transaction_details_ibfk_1` FOREIGN KEY (`transactionId`) REFERENCES `transactions` (`transactionId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_details_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`) ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `roles` (`roleId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
