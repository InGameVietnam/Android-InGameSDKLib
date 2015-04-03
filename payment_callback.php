<?php
$app_secret = '4563b0fde5eb26fccee6e6a6d5f6e784';

$status = $_GET['status'];
$app_id = $_GET['app_id'];
$transaction_id = $_GET['transaction_id'];
$transaction_type = $_GET['transaction_type'];
$amount = $_GET['amount'];
$currency = $_GET['currency'];
$game_order = $_GET['game_order'];
$country_code = $_GET['country_code'];
$response_time = $_GET['response_time'];
$user_id = $_GET['user_id'];
$username = $_GET['username'];
$sign = $_GET['sign'];
$hash = $status . $app_id . $transaction_id . $transaction_type . $amount . $currency . $game_order . $country_code . $response_time . $user_id . $username;

if($status == 1) {
	if($transaction_type == 'CARD'){
		$card_code = $_GET['card_code'];
		$card_serial = $_GET['card_serial'];
		$card_vendor = $_GET['card_vendor'];
		$hash .= $card_code . $card_serial . $card_vendor . $app_secret;
		$hash = md5($hash);
		if($sign == $hash){
			// card
			echo "{\"status\": \"1\"}";
			exit;
		}
	}
	else if($transaction_type == 'BANK'){
		$bank_id = $_GET['bank_id'];
		$hash .= $bank_id . $app_secret;
		$hash = md5($hash);
		if($sign == $hash){
			// bank
			echo "{\"status\": \"1\"}";
			exit;
		}
	}
	else if($transaction_type == 'GOOGLE'){
		$google_id = $_GET['google_id'];
		$hash .= $google_id . $app_secret;
		$hash = md5($hash);
		if($sign == $hash){
			// google
			echo "{\"status\": \"1\"}";
			exit;
		}
	}
	else if($transaction_type == 'APPLE'){
		$apple_id = $_GET['apple_id'];
		$hash .= $apple_id . $app_secret;
		$hash = md5($hash);
		if($sign == $hash){
			// apple
			echo "{\"status\": \"1\"}";
			exit;
		}
	}
}
echo "{\"status\": \"0\"}";
exit;
?>