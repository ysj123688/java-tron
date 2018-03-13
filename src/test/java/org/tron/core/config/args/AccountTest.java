/*
 * java-tron is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * java-tron is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.tron.core.config.args;

import com.google.protobuf.ByteString;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol.AccountType;

public class AccountTest {

  private Account account = new Account();

  /**
   * init account.
   */
  @Before
  public void setAccount() {
    account.setAccountName("tron");
    account.setAccountType("Normal");
    account.setAddress("4948c2e8a756d9437037dcd8c7e0c73d560ca38d");
    account.setBalance("10000");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetNullAccountNameShouldThrowIllegalArgumentException() {
    account.setAccountName(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetEmptyAccountNameShouldThrowIllegalArgumentException() {
    account.setAccountName("");
  }

  @Test
  public void setAccountNameRight() {
    account.setAccountName("tron-name");

    byte[] bytes = ByteArray.fromString("tron-name");

    if (ArrayUtils.isNotEmpty(bytes)) {
      ByteString accountName = ByteString.copyFrom(bytes);
      Assert.assertEquals(accountName, account.getAccountName());
    }
  }

  @Test
  public void getAccountName() {
    byte[] bytes = ByteArray.fromString("tron");

    if (ArrayUtils.isNotEmpty(bytes)) {
      ByteString accountName = ByteString.copyFrom(bytes);
      Assert.assertEquals(accountName, account.getAccountName());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetNullAccountTypeShouldThrowIllegalArgumentException() {
    account.setAccountType(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetEmptyAccountTypeShouldThrowIllegalArgumentException() {
    account.setAccountType("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetOtherAccountTypeShouldThrowIllegalArgumentException() {
    account.setAccountType("other");
  }

  @Test
  public void setAccountTypeRight() {
    account.setAccountType("Contract");

    Assert.assertEquals(account.getAccountTypeByString("Contract"), account.getAccountType());
  }

  @Test
  public void getAccountType() {
    Assert.assertEquals(account.getAccountTypeByString("Normal"), account.getAccountType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetNullAccountAddressShouldThrowIllegalArgumentException() {
    account.setAddress(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetEmptyAccountAddressShouldThrowIllegalArgumentException() {
    account.setAddress("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetBadFormatAccountAddressShouldThrowIllegalArgumentException() {
    account.setAddress("0123131");
  }

  @Test
  public void setAccountBalanceRight() {
    account.setAddress("92814a458256d9437037dcd8c7e0c7948327154d");
    Assert.assertEquals("92814a458256d9437037dcd8c7e0c7948327154d", account.getAddress());
  }

  @Test
  public void getAccountAddress() {
    Assert.assertEquals("4948c2e8a756d9437037dcd8c7e0c73d560ca38d", account.getAddress());
  }

  @Test
  public void getAccountAddressBytes() {
    byte[] bytes = ByteArray.fromHexString("4948c2e8a756d9437037dcd8c7e0c73d560ca38d");
    Assert.assertArrayEquals(bytes, account.getAddressBytes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetNullAccountBalanceShouldThrowIllegalArgumentException() {
    account.setBalance(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetEmptyAccountBalanceShouldThrowIllegalArgumentException() {
    account.setBalance("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetNonDigitalAccountBalanceShouldThrowIllegalArgumentException() {
    account.setBalance("12a");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetExceedTheMaxAccountBalanceShouldThrowIllegalArgumentException() {
    account.setBalance("9223372036854775808");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetExceedTheMinAccountBalanceShouldThrowIllegalArgumentException() {
    account.setBalance("-9223372036854775809");
  }

  @Test
  public void setMaxAccountBalance() {
    account.setBalance("9223372036854775807");
    Assert.assertEquals(Long.MAX_VALUE, account.getBalance());
  }

  @Test
  public void setMinAccountBalance() {
    account.setBalance("-9223372036854775808");
    Assert.assertEquals(Long.MIN_VALUE, account.getBalance());
  }

  @Test
  public void getAccountBalance() {
    Assert.assertEquals(10000, account.getBalance());
  }

  @Test
  public void testIsAccountType() {
    Assert.assertFalse(account.isAccountType(null));
    Assert.assertFalse(account.isAccountType(""));
    Assert.assertFalse(account.isAccountType("abc"));
    Assert.assertTrue(account.isAccountType("Normal"));
    Assert.assertTrue(account.isAccountType("normal"));
    Assert.assertTrue(account.isAccountType("AssetIssue"));
    Assert.assertTrue(account.isAccountType("assetissue"));
    Assert.assertTrue(account.isAccountType("Contract"));
    Assert.assertTrue(account.isAccountType("contract"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenGetNullAccountTypeByStringShouldThrowIllegalArgumentException() {
    Assert.assertEquals(AccountType.Normal, account.getAccountTypeByString(null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenGetEmptyAccountTypeByStringShouldThrowIllegalArgumentException() {
    Assert.assertEquals(AccountType.Normal, account.getAccountTypeByString(""));
  }

  @Test
  public void testGetAccountTypeByStringRight() {
    Assert.assertEquals(AccountType.Normal, account.getAccountTypeByString("Normal"));
    Assert.assertEquals(AccountType.Normal, account.getAccountTypeByString("normal"));
    Assert.assertEquals(AccountType.AssetIssue, account.getAccountTypeByString("AssetIssue"));
    Assert.assertEquals(AccountType.AssetIssue, account.getAccountTypeByString("assetissue"));
    Assert.assertEquals(AccountType.Contract, account.getAccountTypeByString("Contract"));
    Assert.assertEquals(AccountType.Contract, account.getAccountTypeByString("contract"));
  }
}